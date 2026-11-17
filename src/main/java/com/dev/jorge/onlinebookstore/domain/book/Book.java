package com.dev.jorge.onlinebookstore.domain.book;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Book {
    private UUID id;
    private String title;
    private String author;
    private String isbn;
    private int stock;
    private BigDecimal price;

    public Book() {
    }

    public Book(String title, String author, String isbn, int stock, BigDecimal price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.stock = stock;
        this.price = price;
    }

    public Book(UUID id, String title, String author, String isbn, int stock, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.stock = stock;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
