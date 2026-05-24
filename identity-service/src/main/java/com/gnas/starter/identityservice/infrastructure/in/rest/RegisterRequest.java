package com.gnas.starter.identityservice.infrastructure.in.rest;

import com.gnas.starter.identityservice.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank @Size(min = 4, max = 50) String username,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String password,
        Role role
) {}
