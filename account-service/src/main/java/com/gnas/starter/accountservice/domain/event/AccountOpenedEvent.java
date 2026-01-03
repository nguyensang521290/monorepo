package com.gnas.starter.accountservice.domain.event;

import java.time.LocalDateTime;

public record AccountOpenedEvent(
        Long accountId,
        String accountNumber,
        String customerId,
        String currency,
        LocalDateTime createdAt
) {}
