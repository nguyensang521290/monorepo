package com.gnas.starter.identityservice.infrastructure.in.rest;

public record AuthResponse(
    String accessToken,
    String refreshToken
) {}
