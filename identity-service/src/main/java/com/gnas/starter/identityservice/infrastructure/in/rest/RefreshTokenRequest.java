package com.gnas.starter.identityservice.infrastructure.in.rest;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
    @NotBlank
    String refreshToken
) {}
