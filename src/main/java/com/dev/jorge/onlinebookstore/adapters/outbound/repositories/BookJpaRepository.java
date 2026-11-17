package com.dev.jorge.onlinebookstore.adapters.outbound.repositories;

import com.dev.jorge.onlinebookstore.adapters.outbound.entities.BookJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookJpaRepository extends JpaRepository<BookJpaEntity, UUID> {}
