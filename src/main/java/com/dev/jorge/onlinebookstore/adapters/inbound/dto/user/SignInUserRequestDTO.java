package com.dev.jorge.onlinebookstore.adapters.inbound.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignInUserRequestDTO(
        @NotBlank @Email String email,
        @NotBlank String password
) {}
