package com.dev.jorge.onlinebookstore.adapters.inbound.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderRequestDTO(
        @NotNull UUID bookId,
        @NotNull @PositiveOrZero BigDecimal price,
        @Positive int quantity,
        @Valid UserDetails userDetails
) {
    public record UserDetails(
            @NotBlank String firstName,
            @NotBlank String lastName,
            @NotBlank @Email String email
    ) {}
}
