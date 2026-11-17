package com.dev.jorge.onlinebookstore.application.service;

import com.dev.jorge.onlinebookstore.application.usecases.BookUseCases;
import com.dev.jorge.onlinebookstore.domain.book.Book;
import com.dev.jorge.onlinebookstore.domain.book.BookRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookServiceImpl implements BookUseCases {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    public Book create(Book book) {
        return repository.save(book);
    }

    public Book update(Book book) {
        return repository.save(book);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<Book> getAllBooks(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public Book getBookById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + id));
    }
}
