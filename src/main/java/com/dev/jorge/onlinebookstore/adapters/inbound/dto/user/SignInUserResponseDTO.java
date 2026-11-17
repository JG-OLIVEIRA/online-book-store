package com.dev.jorge.onlinebookstore.adapters.inbound.dto.user;

import java.util.UUID;

public record SignInUserResponseDTO(UUID userId, String token) {}
