package com.dev.jorge.onlinebookstore.adapters.inbound.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CreateBookRequestDTO(
        @NotBlank String title,
        @NotBlank String author,
        @NotBlank String isbn,
        @PositiveOrZero int stock,
        @NotNull @PositiveOrZero BigDecimal price
) { }
