package com.dev.jorge.onlinebookstore.application.ports;

public interface PasswordService {
    String encode(String password);
    boolean matches(String password, String encodedPassword);
}
