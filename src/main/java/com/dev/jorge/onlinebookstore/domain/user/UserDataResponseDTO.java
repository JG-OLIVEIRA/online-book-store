package com.dev.jorge.onlinebookstore.domain.user;

import java.util.UUID;

public record UserDataResponseDTO (UUID id, String username, String email) {}
