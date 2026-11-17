package com.dev.jorge.onlinebookstore.integration;

import com.dev.jorge.onlinebookstore.adapters.outbound.repositories.inventory.InventoryJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.http.HttpResponse;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InventoryApiTests extends ApiTestSupport {
    @Autowired private InventoryJpaRepository inventoryRepository;

    @Test
    void checksAvailabilityAndQuantity() throws Exception {
        String token = authenticatedToken();
        UUID bookId = UUID.randomUUID();
        assertEquals(400, request("GET", "/api/inventory?bookId=" + bookId + "&quantity=0", null, token).statusCode());
        inventoryRepository.save(new InventoryJpaEntity(null, bookId, 2));
        HttpResponse<String> available = request("GET", "/api/inventory?bookId=" + bookId + "&quantity=1", null, token);
        assertEquals(200, available.statusCode());
        assertEquals("true", available.body());
        assertEquals("false", request("GET", "/api/inventory?bookId=" + bookId + "&quantity=3", null, token).body());
    }
}
