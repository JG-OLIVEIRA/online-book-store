package com.dev.jorge.onlinebookstore.application.command.order;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderCommand (
    UUID bookId,
    BigDecimal price,
    int quantity,
    UserDetails userDetails
){
    public record UserDetails(
            String firstName,
            String lastName,
            String email
    ) {}
}
