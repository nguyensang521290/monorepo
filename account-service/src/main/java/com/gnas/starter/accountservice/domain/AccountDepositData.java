package com.gnas.starter.accountservice.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountDepositData(
        Account account,
        BigDecimal amount,
        LocalDateTime depositedAt
) {}
