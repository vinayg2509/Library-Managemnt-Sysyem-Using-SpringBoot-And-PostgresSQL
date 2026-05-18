package com.vinay.services;

import com.vinay.exception.BookException;
import com.vinay.payload.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto createBook() throws BookException;
    List<BookDto> createMultipleBook() throws  BookException;
    void softDeleteBook(Long bookId) throws BookException;
    void hardDeleteBook(Long bookId) throws BookException;
    BookDto updateBook(Long bookId,BookDto bookDto) throws BookException;
    BookDto getBookById(Long bookId) throws BookException;
    BookDto getBookByIsbn(String isbn) throws BookException;
    Long getTotalActiveBook();
    Long getTotalAvaliableBook();

}
