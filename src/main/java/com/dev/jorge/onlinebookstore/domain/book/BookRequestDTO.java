package com.dev.jorge.onlinebookstore.domain.book;

public record BookRequestDTO(String title, String author, String isbn, int stock, double price) { }
