package com.dev.jorge.onlinebookstore.adapters.inbound.controller;

import com.dev.jorge.onlinebookstore.adapters.inbound.dto.order.CreateOrderResponseDTO;
import com.dev.jorge.onlinebookstore.application.command.order.CreateOrderCommand;
import com.dev.jorge.onlinebookstore.application.command.order.CreateOrderResult;
import com.dev.jorge.onlinebookstore.application.usecases.OrderUseCases;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.order.CreateOrderRequestDTO;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderUseCases service;

    public OrderController(OrderUseCases service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderResponseDTO placeOrder(@Valid @RequestBody CreateOrderRequestDTO request) {
        CreateOrderCommand command = new CreateOrderCommand(
                request.bookId(),
                request.price(),
                request.quantity(),
                new CreateOrderCommand.UserDetails(
                        request.userDetails().firstName(),
                        request.userDetails().lastName(),
                        request.userDetails().email()
                )
        );
        CreateOrderResult result = service.placeOrder(command);
        return new CreateOrderResponseDTO(result.orderId());
    }
}
