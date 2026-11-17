package com.dev.jorge.onlinebookstore.integration;

import com.dev.jorge.onlinebookstore.application.usecases.AuthUseCases;
import com.dev.jorge.onlinebookstore.application.usecases.BookUseCases;
import com.dev.jorge.onlinebookstore.application.usecases.OrderUseCases;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.book.CreateBookRequestDTO;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.inventory.CreateInventoryRequestDTO;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.order.CreateOrderRequestDTO;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.user.SignInUserRequestDTO;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.user.SignUpUserRequestDTO;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UseCaseValidationTests {
    @Autowired private AuthUseCases auth;
    @Autowired private BookUseCases books;
    @Autowired private InventoryUseCases inventory;
    @Autowired private OrderUseCases orders;
    @Autowired private Validator validator;

    @Test
    void validatesInventoryRecordBoundaries() {
        assertTrue(validator.validate(new CreateInventoryRequestDTO(-1, false)).stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("quantity")));
        assertTrue(validator.validate(new CreateInventoryRequestDTO(0, false)).isEmpty());
    }

    @Test
    void rejectsInvalidAuthenticationArguments() {
        assertThrows(ConstraintViolationException.class, () -> auth.signUp(null));
        assertThrows(ConstraintViolationException.class, () -> auth.signIn(new SignInUserRequestDTO("bad", "")));
        assertThrows(ConstraintViolationException.class, () -> auth.signUp(
                new SignUpUserRequestDTO("user", "", "Last", "user@example.test", "password")));
    }

    @Test
    void rejectsInvalidBookArguments() {
        assertThrows(ConstraintViolationException.class, () -> books.create(null));
        assertThrows(ConstraintViolationException.class, () -> books.create(
                new CreateBookRequestDTO("", "Author", "isbn", -1, null)));
        assertThrows(ConstraintViolationException.class, () -> books.getBookById(null));
        assertThrows(ConstraintViolationException.class, () -> books.getAllBooks(-1, 1));
        assertThrows(ConstraintViolationException.class, () -> books.getAllBooks(0, 0));
        assertThrows(ConstraintViolationException.class, () -> books.delete(null));
    }

    @Test
    void rejectsInvalidInventoryArguments() {
        assertThrows(ConstraintViolationException.class, () -> inventory.isInStock(null, 1));
        assertThrows(ConstraintViolationException.class, () -> inventory.isInStock(UUID.randomUUID(), 0));
    }

    @Test
    void rejectsInvalidOrderArguments() {
        assertThrows(ConstraintViolationException.class, () -> orders.placeOrder(null));
        assertThrows(ConstraintViolationException.class, () -> orders.placeOrder(
                new CreateOrderRequestDTO(UUID.randomUUID(), BigDecimal.ONE, 0, null)));
        ConstraintViolationException nested = assertThrows(ConstraintViolationException.class, () -> orders.placeOrder(
                new CreateOrderRequestDTO(UUID.randomUUID(), BigDecimal.ONE, 1,
                        new CreateOrderRequestDTO.UserDetails("First", "Last", "invalid"))));
        assertTrue(nested.getConstraintViolations().stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().endsWith("userDetails.email")));
    }
}
