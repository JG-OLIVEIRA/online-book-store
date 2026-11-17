package com.dev.jorge.onlinebookstore.application.command.order;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderResult(
        UUID id,
        UUID bookId,
        BigDecimal price,
        int quantity
) {}
