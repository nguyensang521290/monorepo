package com.gnas.starter.identityservice.infrastructure.in.rest;

public record AuthorizationRequest(
    String method,
    String path,
    String token
) {}
