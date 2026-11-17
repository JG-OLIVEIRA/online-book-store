package com.dev.jorge.onlinebookstore.application.usecases;

import com.dev.jorge.onlinebookstore.domain.user.User;

public interface AuthUseCases {
    User signUp(String username, String email, String password);
    String signIn(String email, String password);
    User getUserByEmail(String email);
    String hashPassword(String password);
}
