package com.dev.jorge.onlinebookstore.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.dev.jorge.onlinebookstore.domain.user.UserRepository;

import java.net.http.HttpResponse;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthApiTests extends ApiTestSupport {
    @Autowired private UserRepository users;
    @Test
    void registersAndAuthenticatesUser() throws Exception {
        String username = UUID.randomUUID().toString();
        String email = username + "@example.test";
        String register = """
                {"username":"%s","firstName":"First","lastName":"Last","email":"%s","password":"password"}
                """.formatted(username, email);
        HttpResponse<String> registered = request("POST", "/api/auth/register", register, null);
        assertEquals(201, registered.statusCode(), registered.body());
        String userId = field(registered.body(), "userId");
        UUID.fromString(userId);
        assertEquals(email, users.findByUsername(username).orElseThrow().getEmail());
        assertTrue(users.findByUsername("missing-" + username).isEmpty());

        HttpResponse<String> duplicate = request("POST", "/api/auth/register", register, null);
        assertEquals(409, duplicate.statusCode());
        assertEquals("Email already exists", field(duplicate.body(), "error"));
        assertEquals(401, request("POST", "/api/auth/login",
                "{\"email\":\"unknown@example.test\",\"password\":\"wrong\"}", null).statusCode());
        assertEquals(401, request("POST", "/api/auth/login",
                "{\"email\":\"" + email + "\",\"password\":\"wrong\"}", null).statusCode());

        HttpResponse<String> login = request("POST", "/api/auth/login",
                "{\"email\":\"" + email + "\",\"password\":\"password\"}", null);
        assertEquals(200, login.statusCode(), login.body());
        assertEquals(userId, field(login.body(), "userId"));
        assertFalse(field(login.body(), "token").isBlank());
        assertEquals(403, request("GET", "/api/books?page=0&size=10", null, null).statusCode());
    }

    @Test
    void rejectsInvalidRequests() throws Exception {
        HttpResponse<String> registration = request("POST", "/api/auth/register", "{}", null);
        assertEquals(400, registration.statusCode());
        assertTrue(field(registration.body(), "error").contains("username:"));
        HttpResponse<String> login = request("POST", "/api/auth/login",
                "{\"email\":\"not-an-email\",\"password\":\"password\"}", null);
        assertEquals(400, login.statusCode());
        assertTrue(field(login.body(), "error").contains("email:"));
        assertEquals(400, request("POST", "/api/auth/login", "{", null).statusCode());
    }
}
