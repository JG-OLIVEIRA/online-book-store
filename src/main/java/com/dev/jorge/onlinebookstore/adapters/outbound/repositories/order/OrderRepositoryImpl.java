package com.dev.jorge.onlinebookstore.adapters.outbound.repositories.order;

import com.dev.jorge.onlinebookstore.adapters.outbound.entities.OrderJpaEntity;
import com.dev.jorge.onlinebookstore.domain.order.Order;
import com.dev.jorge.onlinebookstore.domain.order.OrderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository repository;

    public OrderRepositoryImpl(OrderJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity saved = repository.save(new OrderJpaEntity(order));
        Order result = new Order();
        result.setId(saved.getId());
        result.setBookId(saved.getBookId());
        result.setPrice(saved.getPrice());
        result.setQuantity(saved.getQuantity());
        return result;
    }
}
