package com.dev.jorge.onlinebookstore.application.service;

import com.dev.jorge.onlinebookstore.application.command.order.CreateOrderCommand;
import com.dev.jorge.onlinebookstore.application.command.order.CreateOrderResult;
import com.dev.jorge.onlinebookstore.application.usecases.OrderUseCases;
import com.dev.jorge.onlinebookstore.domain.order.Order;
import com.dev.jorge.onlinebookstore.domain.order.OrderRepository;

import java.math.BigDecimal;

public class OrderServiceImpl implements OrderUseCases {

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public CreateOrderResult placeOrder(CreateOrderCommand command) {
        Order order = new Order(
                command.bookId(),
                command.price().multiply(BigDecimal.valueOf(command.quantity())),
                command.quantity()
        );

        Order created = repository.save(order);
        return new CreateOrderResult(created.getId(), created.getBookId(), created.getPrice(), created.getQuantity());
    }

}
