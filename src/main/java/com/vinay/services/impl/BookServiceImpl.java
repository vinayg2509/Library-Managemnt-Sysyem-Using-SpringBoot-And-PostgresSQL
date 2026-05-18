package com.vinay.services.impl;

import com.vinay.exception.BookException;
import com.vinay.payload.dto.BookDto;
import com.vinay.services.BookService;

import java.util.List;

public class BookServiceImpl implements BookService
{

    @Override
    public BookDto createBook() throws BookException {
        return null;
    }

    @Override
    public List<BookDto> createMultipleBook() throws BookException {
        return List.of();
    }

    @Override
    public void softDeleteBook(Long bookId) throws BookException {

    }

    @Override
    public void hardDeleteBook(Long bookId) throws BookException {

    }

    @Override
    public BookDto updateBook(Long bookId, BookDto bookDto) throws BookException {
        return null;
    }

    @Override
    public BookDto getBookById(Long bookId) throws BookException {
        return null;
    }

    @Override
    public BookDto getBookByIsbn(String isbn) throws BookException {
        return null;
    }

    @Override
    public Long getTotalActiveBook() {
        return 0L;
    }

    @Override
    public Long getTotalAvaliableBook() {
        return 0L;
    }
}
