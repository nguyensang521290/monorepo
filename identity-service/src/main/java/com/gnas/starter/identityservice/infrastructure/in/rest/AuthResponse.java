package com.gnas.starter.identityservice.infrastructure.in.rest;

public record AuthResponse(
    Long userId,
    String accessToken,
    String refreshToken
) {}
