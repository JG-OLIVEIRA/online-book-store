package com.dev.jorge.onlinebookstore.domain.order;

import java.math.BigDecimal;
import java.util.UUID;

public class Order {
    private UUID id;
    private UUID bookId;
    private BigDecimal price;
    private int quantity;

    public Order () {}

    public Order(UUID bookId, BigDecimal price, int quantity) {
        this.bookId = bookId;
        this.price = price;
        this.quantity = quantity;
    }

    public Order(UUID id, UUID bookId, BigDecimal price, int quantity) {
        this.id = id;
        this.bookId = bookId;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
