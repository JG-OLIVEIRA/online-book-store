package com.dev.jorge.onlinebookstore.application.command.book;

import java.math.BigDecimal;
import java.util.UUID;

public record GetBookByIdResult(
        UUID id,
        String title,
        String author,
        String isbn,
        int stock,
        BigDecimal price
) {
}
