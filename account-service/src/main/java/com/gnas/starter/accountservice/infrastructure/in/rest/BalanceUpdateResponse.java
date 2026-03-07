package com.gnas.starter.accountservice.infrastructure.in.rest;

import java.math.BigDecimal;

public record BalanceUpdateResponse(Long accountId, BigDecimal newBalance) {
}
