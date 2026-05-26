package com.vinay.services.impl;

import com.vinay.exception.BookException;
import com.vinay.mapper.BookMapper;
import com.vinay.model.Book;
import com.vinay.payload.dto.BookDto;
import com.vinay.payload.request.BookSearchRequest;
import com.vinay.payload.response.PageResponse;
import com.vinay.repository.BookRepository;
import com.vinay.services.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService
{
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto createBook(BookDto bookDto) throws BookException {
       if(bookRepository.existsByIsbn(bookDto.getIsbn())) {
           throw new BookException("Book with ISBN " + bookDto.getIsbn() + " already exists");
       }

       Book book=bookMapper.toEntity(bookDto);
       if(!book.isAvailableIsValid())
           throw new BookException("Available copies cannot exceed total copies");

       Book savedBook=bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public List<BookDto> createMultipleBook(List<BookDto> bookDtos) throws BookException {
        if(bookDtos==null||bookDtos.isEmpty()) {
            throw new BookException("Book list can't be empty");
        }

        for (BookDto bookDto:bookDtos)
        {
            //check for duplicate isbn in an input list
            long duplicateCount =bookDtos.stream()
                    .filter(b->b.getIsbn().equals(bookDto.getIsbn())).count();
            if(duplicateCount>1)
                throw new BookException("Duplicate ISBN in request: " + bookDto.getIsbn());

            // Validate genre exists (will throw exception if not found)
            if(bookDto.getGenreId()==null)
            {
                throw new BookException("Book genre id required for genre id: " + bookDto.getIsbn());
            }
            // Validate available copies
            if(bookDto.getAvailableCopies()>bookDto.getTotalCopies())
            {
                throw new BookException("Available copies cannot exceed total copies for ISBN: " + bookDto.getIsbn());
            }

            // Check if ISBN already exists in database
            if(bookRepository.existsByIsbn(bookDto.getIsbn()))
            {
                throw  new BookException("Book with ISBN "+bookDto.getIsbn()+" is already exist");
            }
        }
        List<Book> booksToSave=new ArrayList<>();
        for(BookDto bookDto:bookDtos)
        {
            Book book=bookMapper.toEntity(bookDto);
            book.setIsActive(true);
            booksToSave.add(book);
        }
        List<Book> savedBook= bookRepository.saveAll(booksToSave);
        return savedBook.stream().map(bookMapper::toDto)
                .toList();
    }


    @Override
    public void softDeleteBook(Long bookId) throws BookException {

      Book book= bookRepository.findById(bookId)
               .orElseThrow(()->new BookException("Book with "+bookId+" not found"));
        book.setIsActive(false);
        bookRepository.save(book);
    }

    @Override
    public void hardDeleteBook(Long bookId) throws BookException {
     Book book=bookRepository.findById(bookId)
             .orElseThrow(()->new BookException("Book with id "+bookId+" not found"));
     bookRepository.delete(book);
    }

    @Override
    public BookDto updateBook(Long bookId, BookDto bookDto) throws BookException {
     Book existingBook=bookRepository.findById(bookId)
             .orElseThrow(()->new BookException("Book with id "+bookId +" not found"));
     if(bookDto.getAvailableCopies()>bookDto.getTotalCopies())
        throw  new BookException("Available copy can't exceed total copies");

     if(!existingBook.getIsbn().equals(bookDto.getIsbn()))
         throw new BookException("ISBN can't change once book is created");

     bookMapper.toUpdateBook(bookDto,existingBook);

     Book updatedBook=bookRepository.save(existingBook);
     return bookMapper.toDto(updatedBook);
    }

    @Override
    public BookDto getBookById(Long bookId) throws BookException {
     Book book=bookRepository.findById(bookId)
             .orElseThrow(()->new BookException("Book with id "+bookId+" not found"));
     return bookMapper.toDto(book);
    }

    @Override
    public BookDto getBookByIsbn(String isbn) throws BookException {
        Book book= bookRepository.findByIsbn(isbn)
                .orElseThrow(()->new BookException("Book with ISBN "+isbn+" not found"));

        return bookMapper.toDto(book);
    }



    @Override
    public Long getTotalActiveBooks() {
        return bookRepository.availableCopies();
    }

    @Override
    public Long getTotalAvailableBooks() {
        return bookRepository.countByIsActiveTrue();
    }

    @Override
    public PageResponse<BookDto> searchBookWithFilters(BookSearchRequest bookSearchRequest) {
        Pageable pageable=createPageable(
                bookSearchRequest.getPage(),
                bookSearchRequest.getSize(),
                bookSearchRequest.getSortBy(),
                bookSearchRequest.getSortDirection()
        );
        Page<Book> bookPage=bookRepository.searchWithFilters(
                 bookSearchRequest.getSearchTerm(),
                 bookSearchRequest.getGenreId(),
                 bookSearchRequest.getAvailableOnly(),
                pageable
        );
        return convertToPageResponse(bookPage);
    }

    private Pageable createPageable(int page, int size, String sortBy, String sortDirection)
    {
        size = Math.min(Math.max(size, 1), 100);
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of(page, size, sort);
    }

    private PageResponse<BookDto> convertToPageResponse(Page<Book> bookPage)
    {
        List<BookDto>bookDtos=bookPage.getContent()
                .stream().map(bookMapper::toDto).collect(Collectors.toList());

        return  new PageResponse<>(
                bookDtos,
                bookPage.getNumber(),
                bookPage.getTotalPages(),
                bookPage.getTotalElements(),
                bookPage.getSize(),
                bookPage.isFirst(),
                bookPage.isLast(),
                bookPage.isEmpty()
        );
    }
}
