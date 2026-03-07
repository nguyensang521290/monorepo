package com.gnas.starter.accountservice.infrastructure.in.rest;

import java.time.LocalDateTime;

public record OpenAccountResponse(
        String accountNumber,
        String customerId,
        String currency,
        java.math.BigDecimal balance,
        LocalDateTime createdAt
) {}
