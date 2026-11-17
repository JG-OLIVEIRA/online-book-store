package com.dev.jorge.onlinebookstore.adapters.inbound.dto.book;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateBookResponseDTO(
        UUID id,
        String title,
        String author,
        String isbn,
        int stock,
        BigDecimal price
) {}
