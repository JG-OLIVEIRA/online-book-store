package com.dev.jorge.onlinebookstore.application.service;

import com.dev.jorge.onlinebookstore.domain.book.BookRepository;
import com.dev.jorge.onlinebookstore.domain.exception.BookNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class BookServiceTests {
    @Test
    void reportsMissingBook() {
        BookRepository repository = mock(BookRepository.class);
        BookServiceImpl service = new BookServiceImpl(repository);
        assertThrows(BookNotFoundException.class, () -> service.getBookById(UUID.randomUUID()));
    }
}
