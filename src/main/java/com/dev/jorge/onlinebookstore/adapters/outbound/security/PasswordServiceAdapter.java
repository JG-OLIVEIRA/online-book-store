package com.dev.jorge.onlinebookstore.adapters.outbound.security;

import com.dev.jorge.onlinebookstore.application.ports.PasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordServiceAdapter implements PasswordService {
    private final PasswordEncoder encoder;

    public PasswordServiceAdapter(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encode(String password) {
        return encoder.encode(password);
    }

    @Override
    public boolean matches(String password, String encodedPassword) {
        return encoder.matches(password, encodedPassword);
    }
}
