package com.dev.jorge.onlinebookstore.application.service;

import com.dev.jorge.onlinebookstore.application.command.book.CreateBookCommand;
import com.dev.jorge.onlinebookstore.application.command.book.CreateBookResult;
import com.dev.jorge.onlinebookstore.application.command.book.GetBookByIdResult;
import com.dev.jorge.onlinebookstore.application.usecases.BookUseCases;
import com.dev.jorge.onlinebookstore.domain.book.*;

import com.dev.jorge.onlinebookstore.domain.common.PageResult;
import com.dev.jorge.onlinebookstore.domain.exception.BookNotFoundException;
import java.util.UUID;

public class BookServiceImpl implements BookUseCases {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public CreateBookResult create(CreateBookCommand command) {
        Book newBook = new Book(
                command.title(),
                command.author(),
                command.isbn(),
                command.stock(),
                command.price()
        );
        Book created = repository.save(newBook);
        return new CreateBookResult(
                created.getId(),
                created.getTitle(),
                created.getAuthor(),
                created.getIsbn(),
                created.getStock(),
                created.getPrice()
        );
    }

    @Override
    public GetBookByIdResult getBookById(UUID id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return new GetBookByIdResult(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getStock(),
                book.getPrice()
        );
    }

    @Override
    public PageResult<GetBookByIdResult> getAllBooks(int page, int size) {
        PageResult<Book> result = repository.findAll(page, size);
        return new PageResult<>(
                result.content().stream().map(book -> new GetBookByIdResult(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getIsbn(),
                        book.getStock(),
                        book.getPrice()
                )).toList(),
                result.page(),
                result.size(),
                result.totalElements()
        );
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
