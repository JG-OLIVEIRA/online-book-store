package com.dev.jorge.onlinebookstore.application.ports;

import com.dev.jorge.onlinebookstore.domain.user.User;

public interface SessionTokenService {
    String generateSessionToken(User user);
}
