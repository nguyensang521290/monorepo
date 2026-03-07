package com.gnas.starter.accountservice.infrastructure.in.rest;

import com.gnas.starter.accountservice.domain.Account;

import java.time.LocalDateTime;
import java.util.Objects;

public final class AccountMapper {
    private AccountMapper() {}

    public static OpenAccountResponse toResponse(Account a) {
        Objects.requireNonNull(a, "account is required");

        String accountNumber = a.getAccountNumber();
        String customerId = a.getCustomerId();
        String currency = a.getCurrency() != null ? a.getCurrency().toString() : null;
        java.math.BigDecimal balance = a.getBalance();
        LocalDateTime createdAt = a.getCreatedAt();

        return new OpenAccountResponse(accountNumber, customerId, currency, balance, createdAt);
    }
}
