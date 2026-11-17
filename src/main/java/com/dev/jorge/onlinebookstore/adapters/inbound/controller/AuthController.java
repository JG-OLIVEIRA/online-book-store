package com.dev.jorge.onlinebookstore.adapters.inbound.controller;

import com.dev.jorge.onlinebookstore.application.command.user.SignInUserCommand;
import com.dev.jorge.onlinebookstore.application.command.user.SignInUserResult;
import com.dev.jorge.onlinebookstore.application.command.user.SignUpUserCommand;
import com.dev.jorge.onlinebookstore.application.command.user.SignUpUserResult;
import com.dev.jorge.onlinebookstore.application.usecases.AuthUseCases;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.user.SignInUserRequestDTO;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.user.SignUpUserRequestDTO;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.user.SignInUserResponseDTO;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.user.SignUpUserResponseDTO;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthUseCases service;

    public AuthController(AuthUseCases service) {
        this.service = service;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpUserResponseDTO register(@Valid @RequestBody SignUpUserRequestDTO request) {
        SignUpUserCommand command = new SignUpUserCommand(
                request.username(),
                request.firstName(),
                request.lastName(),
                request.email(),
                request.password()
        );
        SignUpUserResult result = service.signUp(command);
        return new SignUpUserResponseDTO(result.userId());
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public SignInUserResponseDTO login(@Valid @RequestBody SignInUserRequestDTO request) {
        SignInUserCommand command = new SignInUserCommand(
                request.email(),
                request.password()
        );
        SignInUserResult result = service.signIn(command);
        return new SignInUserResponseDTO(result.userId(), result.token());
    }
}
