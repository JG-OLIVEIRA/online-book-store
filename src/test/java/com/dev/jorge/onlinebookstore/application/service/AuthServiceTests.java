package com.dev.jorge.onlinebookstore.application.service;

import com.dev.jorge.onlinebookstore.application.ports.PasswordService;
import com.dev.jorge.onlinebookstore.application.ports.SessionTokenService;
import com.dev.jorge.onlinebookstore.domain.exception.DuplicateUserException;
import com.dev.jorge.onlinebookstore.domain.exception.InvalidCredentialsException;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.user.SignInUserRequestDTO;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.user.SignUpUserRequestDTO;
import com.dev.jorge.onlinebookstore.domain.user.UserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthServiceTests {
    @Test
    void rejectsDuplicateUsersAndInvalidCredentialsWithoutIssuingTokens() {
        UserRepository users = mock(UserRepository.class);
        PasswordService passwords = mock(PasswordService.class);
        SessionTokenService tokens = mock(SessionTokenService.class);
        AuthServiceImpl service = new AuthServiceImpl(users, tokens, passwords);
        when(users.existsByEmail("existing@example.test")).thenReturn(true);
        assertThrows(DuplicateUserException.class, () -> service.signUp(
                new SignUpUserRequestDTO("user", "First", "Last", "existing@example.test", "password")));
        assertThrows(InvalidCredentialsException.class, () -> service.signIn(
                new SignInUserRequestDTO("missing@example.test", "password")));
        verifyNoInteractions(passwords, tokens);
    }

    @Test
    void rejectsDuplicateUsername() {
        UserRepository users = mock(UserRepository.class);
        PasswordService passwords = mock(PasswordService.class);
        SessionTokenService tokens = mock(SessionTokenService.class);
        when(users.existsByUsername("user")).thenReturn(true);
        AuthServiceImpl service = new AuthServiceImpl(users, tokens, passwords);
        DuplicateUserException exception = assertThrows(DuplicateUserException.class, () -> service.signUp(
                new SignUpUserRequestDTO("user", "First", "Last", "other@example.test", "password")));
        org.junit.jupiter.api.Assertions.assertEquals("Username already exists", exception.getMessage());
        verify(users, never()).save(any());
        verifyNoInteractions(passwords, tokens);
    }
}
