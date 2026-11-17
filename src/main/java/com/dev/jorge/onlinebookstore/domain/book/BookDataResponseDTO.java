package com.dev.jorge.onlinebookstore.domain.book;

import java.math.BigDecimal;
import java.util.UUID;

public record BookDataResponseDTO(
        UUID id,
        String title,
        String author,
        String isbn,
        int stock,
        BigDecimal price
) {}
