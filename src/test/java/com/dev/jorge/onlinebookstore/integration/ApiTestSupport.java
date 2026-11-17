package com.dev.jorge.onlinebookstore.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class ApiTestSupport {
    @LocalServerPort
    private int port;
    private final HttpClient client = HttpClient.newHttpClient();

    protected HttpResponse<String> request(String method, String path, String body, String token) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path));
        if (token != null) {
            builder.header("Authorization", "Bearer " + token);
        }
        if (body != null) {
            builder.header("Content-Type", "application/json");
        }
        builder.method(method, body == null ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(body));
        return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }

    protected String authenticatedToken() throws Exception {
        String username = UUID.randomUUID().toString();
        String email = username + "@example.test";
        String registration = """
                {"username":"%s","firstName":"First","lastName":"Last","email":"%s","password":"password"}
                """.formatted(username, email);
        HttpResponse<String> registered = request("POST", "/api/auth/register", registration, null);
        assertEquals(201, registered.statusCode(), registered.body());
        HttpResponse<String> login = request("POST", "/api/auth/login",
                "{\"email\":\"" + email + "\",\"password\":\"password\"}", null);
        assertEquals(200, login.statusCode(), login.body());
        return field(login.body(), "token");
    }

    protected static String field(String json, String name) {
        Matcher matcher = Pattern.compile("\"" + name + "\"\\s*:\\s*\"([^\"]+)\"").matcher(json);
        assertTrue(matcher.find(), json);
        return matcher.group(1);
    }
}
