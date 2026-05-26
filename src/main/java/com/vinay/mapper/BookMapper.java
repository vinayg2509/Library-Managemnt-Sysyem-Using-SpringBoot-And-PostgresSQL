package com.vinay.mapper;

import com.vinay.exception.BookException;
import com.vinay.model.Book;
import com.vinay.model.Genre;
import com.vinay.payload.dto.BookDto;
import com.vinay.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {
    private final GenreRepository genreRepository;

    public BookDto toDto(Book book) {
        if (book == null) return null;

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setBookTitle(book.getBookTitle());

        if (book.getBookGenre() != null) {
            bookDto.setGenreId(book.getBookGenre().getId());
            bookDto.setGenreName(book.getBookGenre().getName());
            bookDto.setGenreCode(book.getBookGenre().getCode());
        }

        bookDto.setPublisher(book.getPublisher());
        bookDto.setPublicationDate(book.getPublicationDate());
        bookDto.setLanguage(book.getLanguage());
        bookDto.setNoOfPages(book.getNoOfPages());
        bookDto.setDescription(book.getDescription());
        bookDto.setTotalCopies(book.getTotalCopies());
        bookDto.setAvailableCopies(book.getAvailableCopies());
        bookDto.setPriceOfBook(book.getPriceOfBook());
        bookDto.setCoverImageUrl(book.getCoverImageUrl());
        bookDto.setIsActive(book.getIsActive());
        bookDto.setCreatedAt(book.getCreatedAt());
        bookDto.setUpdatedAt(book.getUpdatedAt());

        return bookDto;
    }

    public Book toEntity(BookDto bookDto) throws BookException {
        Book book = new Book();

        book.setIsbn(bookDto.getIsbn());
        book.setBookAuthor(bookDto.getBookAuthor());
        book.setBookTitle(bookDto.getBookTitle());
        if (bookDto.getGenreId() != null) {
            Genre genre = genreRepository.findById(bookDto.getGenreId())
                    .orElseThrow(() -> new BookException("Genre with ID " + bookDto.getGenreId() + " not found"));
            book.setBookGenre(genre);
        }
        book.setPublisher(bookDto.getPublisher());
        book.setPublicationDate(bookDto.getPublicationDate());
        book.setLanguage(bookDto.getLanguage());
        book.setNoOfPages(bookDto.getNoOfPages());
        book.setDescription(bookDto.getDescription());
        book.setTotalCopies(bookDto.getTotalCopies());
        book.setAvailableCopies(bookDto.getAvailableCopies());
        book.setPriceOfBook(bookDto.getPriceOfBook());
        book.setCoverImageUrl(bookDto.getCoverImageUrl());
        book.setIsActive(true);
        return book;
    }

    public BookDto toUpdateBook(BookDto bookDto, Book book) throws BookException {
        if (bookDto == null || book == null) return null;

        book.setBookTitle(bookDto.getBookTitle());
        book.setBookAuthor(bookDto.getBookAuthor());
        if (bookDto.getGenreId() != null) {
            Genre genre = genreRepository.findById(bookDto.getGenreId())
                    .orElseThrow(() -> new BookException("Genre with ID " + bookDto.getGenreId() + " not found"));
            book.setBookGenre(genre);
        }
        book.setPublisher(bookDto.getPublisher());
        book.setPublicationDate(bookDto.getPublicationDate());
        book.setLanguage(bookDto.getLanguage());
        book.setNoOfPages(bookDto.getNoOfPages());
        book.setDescription(bookDto.getDescription());
        book.setTotalCopies(bookDto.getTotalCopies());
        book.setAvailableCopies(bookDto.getAvailableCopies());
        book.setPriceOfBook(bookDto.getPriceOfBook());
        book.setCoverImageUrl(bookDto.getCoverImageUrl());
        if(bookDto.getIsActive() != null) {
            book.setIsActive(bookDto.getIsActive());
        }
        return toDto(book);
    }
}
