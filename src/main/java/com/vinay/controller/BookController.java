package com.vinay.controller;

import com.vinay.exception.BookException;
import com.vinay.payload.dto.BookDto;
import com.vinay.payload.request.BookSearchRequest;
import com.vinay.payload.response.ApiResponse;
import com.vinay.payload.response.PageResponse;
import com.vinay.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/create-book")
    public ResponseEntity<BookDto>
    createBook(@Valid @RequestBody BookDto bookDto) throws BookException {
        try {
            BookDto createdBook=bookService.createBook(bookDto);
           return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping ("/create-book-bulk")
    public ResponseEntity<?> createBulkBook( @Valid @RequestBody List<BookDto> bookDtos) throws BookException
    {
        try {
            List<BookDto> createdBooks=bookService.createMultipleBook(bookDtos);
            return  new ResponseEntity<>(createdBooks,HttpStatus.CREATED);
        } catch (Exception e) {
           return   ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(),false));
        }
    }

    @GetMapping("/get-book-id/{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long bookId) throws BookException {
        BookDto book=bookService.getBookById(bookId);
        return  ResponseEntity.ok(book);
    }

    @GetMapping("get-book-isbn/{isbn}")
    public ResponseEntity<BookDto> getBookByIsbn(@PathVariable String isbn) throws  BookException
    {
            BookDto bookDto=bookService.getBookByIsbn(isbn);
            return ResponseEntity.ok(bookDto);
    }

    @PutMapping("update-book/{bookId}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long bookId,
                                              @RequestBody BookDto bookDto) throws BookException
    {
            BookDto updateBook=bookService.updateBook(bookId,bookDto);
            return ResponseEntity.ok(updateBook);

    }

    @DeleteMapping("soft-delete/{bookId}")
    public ResponseEntity<ApiResponse> softDelete(@PathVariable Long bookId) throws BookException

    {
        bookService.softDeleteBook(bookId);
        return ResponseEntity.ok(new ApiResponse("Book Delete Successfully",true));
    }

    @DeleteMapping("hard-delete/{bookId}")
    public ResponseEntity<ApiResponse> hardDelete(@PathVariable Long bookId) throws BookException {
        bookService.hardDeleteBook(bookId);
        return ResponseEntity.ok(new ApiResponse("Book Delete Successfully",true));

    }

    @GetMapping("/stats")
    public ResponseEntity<BookStatsResponse> getBookStats() {
        long totalActive = bookService.getTotalActiveBooks();
        long totalAvailable = bookService.getTotalAvailableBooks();

        BookStatsResponse stats = new BookStatsResponse(totalActive, totalAvailable);
        return ResponseEntity.ok(stats);
    }

    public static class BookStatsResponse{
       public long totalActiveBooks;
        public long totalAvailableBooks;

        public BookStatsResponse(long totalActiveBooks, long totalAvailableBooks) {
            this.totalActiveBooks = totalActiveBooks;
            this.totalAvailableBooks = totalAvailableBooks;
        }
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<BookDto>> advancedSearch(
            @RequestBody BookSearchRequest searchRequest) {

        PageResponse<BookDto> books = bookService.searchBookWithFilters(searchRequest);
        return ResponseEntity.ok(books);
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookDto>>searchBooks(
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false,defaultValue = "false") Boolean availableOnly,
            @RequestParam(defaultValue = "true") boolean activeOnly,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        // Build search request from query parameters
        BookSearchRequest searchRequest = new BookSearchRequest();
        searchRequest.setGenreId(genreId);
        searchRequest.setAvailableOnly(availableOnly);
        searchRequest.setPage(page);
        searchRequest.setSize(size);
        searchRequest.setSortBy(sortBy);
        searchRequest.setSortDirection(sortDirection);

        PageResponse<BookDto> books = bookService.searchBookWithFilters(searchRequest);
        return ResponseEntity.ok(books);
    }
}
