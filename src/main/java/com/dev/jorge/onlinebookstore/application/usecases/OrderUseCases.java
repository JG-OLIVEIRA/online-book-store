package com.dev.jorge.onlinebookstore.application.usecases;

import com.dev.jorge.onlinebookstore.application.command.order.CreateOrderCommand;
import com.dev.jorge.onlinebookstore.application.command.order.CreateOrderResult;

public interface OrderUseCases {
    CreateOrderResult placeOrder(CreateOrderCommand command);
}
