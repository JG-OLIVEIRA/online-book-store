package com.dev.jorge.onlinebookstore.application.command.user;

import java.util.UUID;

public record SignUpUserResult(
        UUID userId,
        String username,
        String email
) {}

