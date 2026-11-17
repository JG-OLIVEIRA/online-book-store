package com.dev.jorge.onlinebookstore.infrastructure.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dev.jorge.onlinebookstore.domain.user.Role;
import com.dev.jorge.onlinebookstore.domain.user.User;
import com.dev.jorge.onlinebookstore.domain.user.UserRepository;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityTests {
    private static final String SECRET = "a-test-secret-for-token-signing";

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Busca usuario por nome ou email e rejeita identificador ausente")
    void loadsUsers() {
        UserRepository repository = mock(UserRepository.class);
        User user = new User(UUID.randomUUID(), "user", "First", "Last", "mail@example.test", "hash");
        when(repository.findByUsername("user")).thenReturn(Optional.of(user));
        when(repository.findByEmail("mail@example.test")).thenReturn(Optional.of(user));
        AuthConfig config = new AuthConfig(repository);
        UserDetails details = config.loadUserByUsername("user");
        assertEquals("user", details.getUsername());
        assertEquals("hash", details.getPassword());
        assertTrue(details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(Role.ROLE_USER.name())));
        assertTrue(details.isAccountNonExpired());
        assertTrue(details.isAccountNonLocked());
        assertTrue(details.isCredentialsNonExpired());
        assertTrue(details.isEnabled());
        assertEquals("user", config.loadUserByUsername("mail@example.test").getUsername());
        assertThrows(UsernameNotFoundException.class, () -> config.loadUserByUsername("missing"));
    }

    @Test
    @DisplayName("Valida tokens assinados e rejeita tokens invalidos ou sem usuario")
    void validatesTokens() {
        TokenService tokens = new TokenService();
        ReflectionTestUtils.setField(tokens, "secret", SECRET);
        User user = new User(UUID.randomUUID(), "user", "First", "Last", "mail@example.test", "hash");
        JWTUserData data = tokens.validateSessionToken(tokens.generateSessionToken(user)).orElseThrow();
        assertEquals(user.getId(), data.userId());
        assertEquals(user.getEmail(), data.email());
        assertEquals(1, data.roles().size());
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        assertTrue(tokens.validateSessionToken(JWT.create().sign(algorithm)).isEmpty());
        assertTrue(tokens.validateSessionToken(JWT.create().withClaim("userId", "not-a-uuid").sign(algorithm)).isEmpty());
        assertTrue(tokens.validateSessionToken("invalid-token").isEmpty());
        assertTrue(tokens.validateSessionToken(JWT.create().withClaim("userId", user.getId().toString())
                .sign(algorithm)).orElseThrow().roles().isEmpty());
    }

    @Test
    @DisplayName("Filtro rejeita tokens invalidos sem interromper a cadeia")
    void filterProcessesHeaders() throws Exception {
        TokenService tokens = mock(TokenService.class);
        SecurityFilter filter = new SecurityFilter(tokens);
        FilterChain chain = mock(FilterChain.class);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic value");
        filter.doFilterInternal(request, response, chain);
        verifyNoInteractions(tokens);
        MockHttpServletRequest invalid = new MockHttpServletRequest();
        invalid.addHeader("Authorization", "Bearer invalid");
        when(tokens.validateSessionToken("invalid")).thenReturn(Optional.empty());
        filter.doFilterInternal(invalid, response, chain);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain, times(2)).doFilter(any(), any());
        assertThrows(NullPointerException.class, () -> filter.doFilterInternal(null, response, chain));
        assertThrows(NullPointerException.class, () -> filter.doFilterInternal(request, null, chain));
        assertThrows(NullPointerException.class, () -> filter.doFilterInternal(request, response, null));
    }
}
