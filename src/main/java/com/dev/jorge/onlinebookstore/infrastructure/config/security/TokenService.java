package com.dev.jorge.onlinebookstore.infrastructure.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dev.jorge.onlinebookstore.domain.user.Role;
import com.dev.jorge.onlinebookstore.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${JWT_SECRET:secret}")
    private String secret;

    public String generateSessionToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim("userId", user.getId().toString())
                .withClaim("roles", user.getRoles().stream().map(Role::name).toList())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plus(Duration.ofHours(7)))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateSessionToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decoded = JWT.require(algorithm).build().verify(token);

            if (decoded.getClaim("userId").isMissing()) {
                return Optional.empty();
            }

            List<String> roles = decoded.getClaim("roles").asList(String.class);

            return Optional.of(JWTUserData.builder()
                    .userId(UUID.fromString(decoded.getClaim("userId").asString()))
                    .email(decoded.getSubject())
                    .roles(roles != null ? roles : Collections.emptyList())
                    .build());

        } catch (JWTVerificationException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
