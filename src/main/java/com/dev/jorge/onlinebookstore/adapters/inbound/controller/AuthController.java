package com.dev.jorge.onlinebookstore.adapters.inbound.controller;

import com.dev.jorge.onlinebookstore.application.service.AuthServiceImpl;
import com.dev.jorge.onlinebookstore.domain.user.User;
import com.dev.jorge.onlinebookstore.domain.user.UserDataResponseDTO;
import com.dev.jorge.onlinebookstore.domain.user.UserRequestDTO;
import com.dev.jorge.onlinebookstore.domain.user.UserResponseDTO;
import com.dev.jorge.onlinebookstore.utils.mappers.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServiceImpl service;
    private final UserMapper mapper;

    public AuthController(AuthServiceImpl service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDataResponseDTO> register(UserRequestDTO request) {
        User user = service.signUp(request.username(), request.email(), request.password());
        return ResponseEntity.ok(mapper.toDTO(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(UserRequestDTO request) {
        String token = service.signIn(request.email(), request.password());
        return ResponseEntity.ok(new UserResponseDTO(token));
    }
}
