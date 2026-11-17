package com.dev.jorge.onlinebookstore.adapters.outbound.repositories.order;

import com.dev.jorge.onlinebookstore.adapters.outbound.entities.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> { }
