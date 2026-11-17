package com.dev.jorge.onlinebookstore.application.command.user;

public record SignUpUserCommand(
         String username,
         String firstName,
         String lastName,
         String email,
         String password
){ }
