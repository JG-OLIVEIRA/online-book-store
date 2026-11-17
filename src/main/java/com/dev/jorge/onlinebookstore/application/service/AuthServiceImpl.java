package com.dev.jorge.onlinebookstore.application.service;

import com.dev.jorge.onlinebookstore.application.usecases.AuthUseCases;
import com.dev.jorge.onlinebookstore.domain.user.User;
import com.dev.jorge.onlinebookstore.domain.user.UserRepository;
import com.dev.jorge.onlinebookstore.domain.user.Role;
import com.dev.jorge.onlinebookstore.infrastructure.config.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthUseCases {

    private final UserRepository repository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(
            UserRepository repository,
            TokenService tokenService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signUp(String username, String email, String password) {
        if(repository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        if(repository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(hashPassword(password));
        newUser.setRoles(Set.of(Role.ROLE_USER));

        return repository.save(newUser);
    }

    public String signIn(String email, String password) {
        User user = getUserByEmail(email);

        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(email, password);
        if(authenticationManager.authenticate(userAndPass).isAuthenticated()){
            return tokenService.generateSessionToken(user);
        }
        throw new BadCredentialsException("Credenciais inválidas");
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

}
