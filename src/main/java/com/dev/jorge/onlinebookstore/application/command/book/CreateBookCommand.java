package com.dev.jorge.onlinebookstore.application.command.book;

import java.math.BigDecimal;

public record CreateBookCommand (
    String title,
    String author,
    String isbn,
    int stock,
    BigDecimal price
) {}
