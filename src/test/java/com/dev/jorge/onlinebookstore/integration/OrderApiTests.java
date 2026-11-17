package com.dev.jorge.onlinebookstore.integration;

import com.dev.jorge.onlinebookstore.adapters.outbound.repositories.order.OrderJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.http.HttpResponse;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderApiTests extends ApiTestSupport {
    @Autowired private OrderJpaRepository orderRepository;

    @Test
    void validatesAndPersistsOrders() throws Exception {
        String token = authenticatedToken();
        UUID bookId = UUID.randomUUID();
        HttpResponse<String> invalid = request("POST", "/api/orders",
                "{\"bookId\":\"" + bookId + "\",\"price\":-1,\"quantity\":0,"
                        + "\"userDetails\":{\"firstName\":\"First\",\"lastName\":\"Last\",\"email\":\"invalid\"}}", token);
        assertEquals(400, invalid.statusCode());
        String errors = field(invalid.body(), "error");
        assertTrue(errors.contains("quantity:"), errors);
        assertTrue(errors.contains("price:"), errors);
        assertTrue(errors.contains("userDetails.email:"), errors);
        assertEquals(201, request("POST", "/api/orders",
                "{\"bookId\":\"" + bookId + "\",\"price\":12.50,\"quantity\":1}", token).statusCode());
        assertTrue(orderRepository.findAll().stream().anyMatch(order ->
                order.getBookId().equals(bookId) && order.getQuantity() == 1));
    }
}
