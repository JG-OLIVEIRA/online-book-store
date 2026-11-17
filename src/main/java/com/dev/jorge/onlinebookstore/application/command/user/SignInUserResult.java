package com.dev.jorge.onlinebookstore.application.command.user;

import java.util.UUID;

public record SignInUserResult (UUID userId, String token){ }
