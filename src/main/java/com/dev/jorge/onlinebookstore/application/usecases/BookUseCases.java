package com.dev.jorge.onlinebookstore.application.usecases;

import com.dev.jorge.onlinebookstore.domain.book.Book;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface BookUseCases {
    Book create(Book book);
    Book update(Book book);
    void delete(UUID id);
    Page<Book> getAllBooks(int page, int size);
    Book getBookById(UUID id);
}
