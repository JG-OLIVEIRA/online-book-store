package com.dev.jorge.onlinebookstore.adapters;

import com.dev.jorge.onlinebookstore.adapters.inbound.mapper.BookMapper;
import com.dev.jorge.onlinebookstore.adapters.inbound.mapper.OrderMapper;
import com.dev.jorge.onlinebookstore.adapters.outbound.entities.BookJpaEntity;
import com.dev.jorge.onlinebookstore.adapters.outbound.entities.UserJpaEntity;
import com.dev.jorge.onlinebookstore.adapters.outbound.repositories.book.BookPersistenceMapper;
import com.dev.jorge.onlinebookstore.adapters.outbound.repositories.user.UserMapper;
import com.dev.jorge.onlinebookstore.domain.book.Book;
import com.dev.jorge.onlinebookstore.domain.order.Order;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.order.CreateOrderRequestDTO;
import com.dev.jorge.onlinebookstore.domain.user.Role;
import com.dev.jorge.onlinebookstore.domain.user.User;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.user.SignUpUserRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MapperTests {
    @Test
    @DisplayName("Mapeadores de livros tratam valores nulos")
    void bookMappersHandleNull() {
        BookMapper api = Mappers.getMapper(BookMapper.class);
        BookPersistenceMapper persistence = Mappers.getMapper(BookPersistenceMapper.class);
        assertNull(api.toDataResponseDTO(null));
        assertNull(api.toResponseDTO(null));
        assertNull(persistence.toDomain(null));
        UUID id = UUID.randomUUID();
        Book book = persistence.toDomain(new BookJpaEntity(id, "Title", "Author", "ISBN", 2, BigDecimal.TEN));
        assertEquals(id, api.toResponseDTO(book).id());
        assertEquals(2, api.toDataResponseDTO(book).stock());
    }

    @Test
    @DisplayName("Mapeador de pedidos transfere dados e preserva identificador nao gerado")
    void mapsOrders() {
        OrderMapper mapper = Mappers.getMapper(OrderMapper.class);
        assertNull(mapper.toDomain(null));
        UUID id = UUID.randomUUID();
        Order order = mapper.toDomain(new CreateOrderRequestDTO(id, BigDecimal.TEN, 2, null));
        assertNull(order.getId());
        assertEquals(id, order.getBookId());
        assertEquals(BigDecimal.TEN, order.getPrice());
        assertEquals(2, order.getQuantity());
    }

    @Test
    @DisplayName("Mapeador de usuarios preserva dados e trata papeis nulos")
    void mapsUsers() {
        UserMapper mapper = Mappers.getMapper(UserMapper.class);
        assertNull(mapper.toDomain((UserJpaEntity) null));
        assertNull(mapper.toDomain((SignUpUserRequestDTO) null));
        User mapped = mapper.toDomain(new SignUpUserRequestDTO("user", "First", "Last", "email", "password"));
        assertNull(mapped.getId());
        assertEquals("First", mapped.getFirstName());
        assertEquals("email", mapped.getEmail());
        UserJpaEntity entity = new UserJpaEntity();
        entity.setUsername("user");
        entity.setRoles(null);
        assertEquals("user", mapper.toDomain(entity).getUsername());
        entity.setRoles(Set.of(Role.ROLE_USER));
        assertEquals(Set.of(Role.ROLE_USER), mapper.toDomain(entity).getRoles());
    }
}
