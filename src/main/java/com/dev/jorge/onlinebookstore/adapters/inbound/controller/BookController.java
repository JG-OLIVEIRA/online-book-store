package com.dev.jorge.onlinebookstore.adapters.inbound.controller;

import com.dev.jorge.onlinebookstore.application.usecases.BookUseCases;
import com.dev.jorge.onlinebookstore.domain.book.Book;
import com.dev.jorge.onlinebookstore.domain.book.BookRequestDTO;
import com.dev.jorge.onlinebookstore.domain.book.BookResponseDTO;
import com.dev.jorge.onlinebookstore.utils.mappers.BookMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookUseCases service;
    private final BookMapper mapper;

    public BookController(BookUseCases service, BookMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> addBook(BookRequestDTO request) {
        Book book = service.create(request.title(), request.author(), request.isbn(), request.stock(), request.price());
        return ResponseEntity.ok(mapper.toDTO(book));
    }

}
