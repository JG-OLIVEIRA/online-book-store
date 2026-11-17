package com.dev.jorge.onlinebookstore.integration;

import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BookApiTests extends ApiTestSupport {
    @Test
    void createsAndRetrievesBooks() throws Exception {
        String token = authenticatedToken();
        HttpResponse<String> created = request("POST", "/api/books",
                "{\"title\":\"Book\",\"author\":\"Author\",\"isbn\":\"123\",\"stock\":3,\"price\":12.50}", token);
        assertEquals(201, created.statusCode(), created.body());
        String bookId = field(created.body(), "id");
        UUID.fromString(bookId);
        HttpResponse<String> book = request("GET", "/api/books/" + bookId, null, token);
        assertEquals(200, book.statusCode(), book.body());
        assertTrue(book.body().contains("\"stock\":3"), book.body());
        HttpResponse<String> page = request("GET", "/api/books?page=0&size=10", null, token);
        assertEquals(200, page.statusCode(), page.body());
        assertTrue(page.body().contains(bookId), page.body());
        HttpResponse<String> missing = request("GET", "/api/books/" + UUID.randomUUID(), null, token);
        assertEquals(404, missing.statusCode());
        assertTrue(field(missing.body(), "error").startsWith("Book not found:"));
        assertEquals(204, request("DELETE", "/api/books/" + bookId, null, token).statusCode());
        assertEquals(404, request("GET", "/api/books/" + bookId, null, token).statusCode());
    }

    @Test
    void rejectsInvalidBookAndPagination() throws Exception {
        String token = authenticatedToken();
        HttpResponse<String> invalidBook = request("POST", "/api/books",
                "{\"title\":\" \",\"author\":\"Author\",\"isbn\":\"123\",\"stock\":-1,\"price\":-1}", token);
        assertEquals(400, invalidBook.statusCode());
        String errors = field(invalidBook.body(), "error");
        assertTrue(errors.contains("title:"), errors);
        assertTrue(errors.contains("stock:"), errors);
        assertTrue(errors.contains("price:"), errors);
        HttpResponse<String> invalidPage = request("GET", "/api/books?page=-1&size=10", null, token);
        assertEquals(400, invalidPage.statusCode());
        assertTrue(field(invalidPage.body(), "error").contains("getAllBooks"));
        assertEquals(400, request("GET", "/api/books?page=abc&size=10", null, token).statusCode());
    }
}
