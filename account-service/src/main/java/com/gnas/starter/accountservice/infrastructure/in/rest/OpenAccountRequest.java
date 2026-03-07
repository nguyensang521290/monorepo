package com.gnas.starter.accountservice.infrastructure.in.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record OpenAccountRequest(
        @NotBlank(message = "customerId is required") String customerId,
        @NotBlank(message = "currency is required")
        @Pattern(regexp = "^[A-Z]{3}$", message = "currency must be a 3-letter ISO code") String currency
) {}
