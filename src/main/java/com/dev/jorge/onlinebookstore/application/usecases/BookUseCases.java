package com.dev.jorge.onlinebookstore.application.usecases;

import com.dev.jorge.onlinebookstore.application.command.book.CreateBookCommand;
import com.dev.jorge.onlinebookstore.application.command.book.CreateBookResult;
import com.dev.jorge.onlinebookstore.application.command.book.GetBookByIdResult;

import com.dev.jorge.onlinebookstore.domain.common.PageResult;

import java.util.UUID;

public interface BookUseCases {
    CreateBookResult create(CreateBookCommand request);
    GetBookByIdResult getBookById(UUID id);
    PageResult<GetBookByIdResult> getAllBooks(int page, int size);
    void delete(UUID id);
}
