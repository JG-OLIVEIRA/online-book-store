package com.dev.jorge.onlinebookstore.domain.book;

import com.dev.jorge.onlinebookstore.domain.common.PageResult;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(UUID id);
    PageResult<Book> findAll(int page, int size);
    void deleteById(UUID id);
}
