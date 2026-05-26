package com.vinay.services;

import com.vinay.exception.BookException;
import com.vinay.payload.dto.BookDto;
import com.vinay.payload.request.BookSearchRequest;
import com.vinay.payload.response.PageResponse;

import java.util.List;

public interface BookService {

    BookDto createBook(BookDto bookDto) throws BookException;
    List<BookDto> createMultipleBook(List<BookDto> bookDtos) throws  BookException;
    void softDeleteBook(Long bookId) throws BookException;
    void hardDeleteBook(Long bookId) throws BookException;
    BookDto updateBook(Long bookId,BookDto bookDto) throws BookException;
    BookDto getBookById(Long bookId) throws BookException;
    BookDto getBookByIsbn(String isbn) throws BookException;
    Long getTotalActiveBooks();
    Long getTotalAvailableBooks();
    PageResponse <BookDto> searchBookWithFilters(BookSearchRequest bookSearchRequest);
}
