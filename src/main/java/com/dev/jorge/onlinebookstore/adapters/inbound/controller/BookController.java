package com.dev.jorge.onlinebookstore.adapters.inbound.controller;

import com.dev.jorge.onlinebookstore.adapters.inbound.dto.book.GetBookResponseDTO;
import com.dev.jorge.onlinebookstore.application.command.book.CreateBookCommand;
import com.dev.jorge.onlinebookstore.application.command.book.CreateBookResult;
import com.dev.jorge.onlinebookstore.application.command.book.GetBookByIdResult;
import com.dev.jorge.onlinebookstore.application.usecases.BookUseCases;

import com.dev.jorge.onlinebookstore.adapters.inbound.dto.book.CreateBookResponseDTO;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.book.CreateBookRequestDTO;
import com.dev.jorge.onlinebookstore.domain.common.PageResult;
import com.dev.jorge.onlinebookstore.adapters.inbound.mapper.BookMapper;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBookResponseDTO createBook(@Valid @RequestBody CreateBookRequestDTO request) {
        CreateBookCommand command = new CreateBookCommand(
                request.title(),
                request.author(),
                request.isbn(),
                request.stock(),
                request.price()
        );
        CreateBookResult result = service.create(command);
        return mapper.toCreateBookResponseDTO(result);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetBookResponseDTO getBookById(@PathVariable UUID id) {
        GetBookByIdResult result = service.getBookById(id);
        return mapper.toGetBookResponseDTO(result);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<GetBookResponseDTO> getAllBooks(@RequestParam int page, @RequestParam int size) {
        PageResult<GetBookByIdResult> result = service.getAllBooks(page, size);
        return new PageImpl<>(
                result.content().stream().map(mapper::toGetBookResponseDTO).toList(),
                PageRequest.of(result.page(), result.size()),
                result.totalElements()
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable UUID id) {
        service.delete(id);
    }

}
