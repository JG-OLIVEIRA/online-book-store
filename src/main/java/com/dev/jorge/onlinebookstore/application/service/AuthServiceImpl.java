package com.dev.jorge.onlinebookstore.application.service;

import com.dev.jorge.onlinebookstore.application.command.user.SignInUserCommand;
import com.dev.jorge.onlinebookstore.application.command.user.SignInUserResult;
import com.dev.jorge.onlinebookstore.application.command.user.SignUpUserCommand;
import com.dev.jorge.onlinebookstore.application.command.user.SignUpUserResult;
import com.dev.jorge.onlinebookstore.application.usecases.AuthUseCases;
import com.dev.jorge.onlinebookstore.application.ports.PasswordService;
import com.dev.jorge.onlinebookstore.application.ports.SessionTokenService;
import com.dev.jorge.onlinebookstore.domain.exception.DuplicateUserException;
import com.dev.jorge.onlinebookstore.domain.exception.InvalidCredentialsException;
import com.dev.jorge.onlinebookstore.domain.user.*;

public class AuthServiceImpl implements AuthUseCases {

    private final UserRepository repository;
    private final SessionTokenService tokenService;
    private final PasswordService passwordService;

    public AuthServiceImpl(
            UserRepository repository,
            SessionTokenService tokenService,
            PasswordService passwordService
    ) {
        this.repository = repository;
        this.tokenService = tokenService;
        this.passwordService = passwordService;
    }

    @Override
    public SignUpUserResult signUp(SignUpUserCommand command) {
        String email = command.email();
        String username = command.username();

        if(repository.existsByEmail(email)) {
            throw new DuplicateUserException("Email already exists");
        }

        if(repository.existsByUsername(username)) {
            throw new DuplicateUserException("Username already exists");
        }

        User newUser = new User(username, command.firstName(), command.lastName(), email, passwordService.encode(command.password()));

        User createdUser = repository.save(newUser);

        return new SignUpUserResult(createdUser.getId(), username, email);
    }

    @Override
    public SignInUserResult signIn(SignInUserCommand command) {
        String email = command.email();
        String password = command.password();

        User user = repository.findByEmail(email).orElseThrow(InvalidCredentialsException::new);
        if (!passwordService.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        String token = tokenService.generateSessionToken(user);
        return new SignInUserResult(token);
    }

}
