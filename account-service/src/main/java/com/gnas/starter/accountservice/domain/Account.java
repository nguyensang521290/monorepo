package com.gnas.starter.accountservice.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {
    private Long id;
    private String customerId;
    private String accountNumber;
    private String currency;
    private BigDecimal balance;
    private AccountStatus status;
    private LocalDateTime createdAt;

    private final List<Object> domainEvents = new ArrayList<>();

    public static Account openAccount(String customerId, String accountNumber, String currency) {
        if (customerId == null) {
            throw new IllegalArgumentException("customerId must not be null!");
        } else if (accountNumber == null ) {
            throw new IllegalArgumentException("accountNumber must not be null!");
        } else if (currency == null) {
            throw new IllegalArgumentException("currency must not be null!");
        } else {
            Account newAccount = new Account();
            newAccount.customerId = customerId;
            newAccount.accountNumber = accountNumber;
            newAccount.currency = currency;
            newAccount.balance = BigDecimal.ZERO;
            newAccount.status = AccountStatus.ACTIVE;
            return newAccount;
        }
    }

    public static Account reconstitute(Long id, String customerId, String accountNumber, String currency, BigDecimal balance, AccountStatus status, LocalDateTime createdAt) {
        Account account = new Account();
        account.setId(id);
        account.customerId = customerId;
        account.accountNumber = accountNumber;
        account.currency = currency;
        account.balance = balance;
        account.status = status;
        account.createdAt = createdAt;
        return account;
    }

    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive!");
        }
        this.balance = this.balance.add(amount);
    }

    public void freeze() {}

    public void unfreeze() {}

    public void close() {}

    // Business rule validation
    private static boolean isValidCurrency(String currency) {
        return currency != null &&
                (currency.equalsIgnoreCase("USD") ||
                        currency.equalsIgnoreCase("VND") ||
                        currency.equalsIgnoreCase("EUR"));
    }

    private void raiseEvent(Object event) {
        this.domainEvents.add(event);
    }

    public List<Object> getDomainEvents() {
        return new ArrayList<>(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

    // For repository to set ID after persistence
    void setId(Long id) {
        this.id = id;
    }
}