package com.dev.jorge.onlinebookstore.adapters.outbound.entities;

import com.dev.jorge.onlinebookstore.domain.order.Order;
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

@Table(name = "TB_ORDER")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderJpaEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private UUID bookId;
    private BigDecimal price;
    private int quantity;

    public OrderJpaEntity(Order order) {
        this.bookId = order.getBookId();
        this.price = order.getPrice();
        this.quantity = order.getQuantity();
    }
}
