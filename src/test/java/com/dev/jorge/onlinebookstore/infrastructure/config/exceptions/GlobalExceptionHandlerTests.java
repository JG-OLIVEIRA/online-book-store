package com.dev.jorge.onlinebookstore.infrastructure.config.exceptions;

import com.dev.jorge.onlinebookstore.domain.exception.InvalidInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTests {
    @Test
    @DisplayName("Erro de entrada invalida conserva mensagem e retorna 400")
    void handlesInvalidInput() {
        var response = new GlobalExceptionHandler().handleInvalidInput(new InvalidInputException("Invalid input"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid input", response.getBody().get("error"));
    }
}
