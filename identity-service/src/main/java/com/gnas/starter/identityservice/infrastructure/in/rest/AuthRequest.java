package com.gnas.starter.identityservice.infrastructure.in.rest;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank String username,
        @NotBlank String password
) {}
