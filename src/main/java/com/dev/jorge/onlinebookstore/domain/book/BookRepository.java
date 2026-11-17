package com.dev.jorge.onlinebookstore.domain.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(UUID id);

    Page<Book> findAll(int page, int size);

    void deleteById(UUID id);
    Page<Book> findAll(PageRequest pageRequest);
}
