package com.dev.jorge.onlinebookstore.infrastructure.config.security;

import com.dev.jorge.onlinebookstore.domain.user.User;
import com.dev.jorge.onlinebookstore.domain.user.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.jspecify.annotations.NonNull;

import org.springframework.stereotype.Service;

@Service
public class AuthConfig implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(username))
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new AutheticatedUser(user);
    }
}
