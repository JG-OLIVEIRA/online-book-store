package com.dev.jorge.onlinebookstore.adapters.outbound.entities;

import com.dev.jorge.onlinebookstore.domain.book.Book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "TB_BOOKS")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookJpaEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String author;
    private String isbn;
    private int stock;
    private BigDecimal price;

    public BookJpaEntity(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
        this.stock = book.getStock();
        this.price = book.getPrice();
    }

}
