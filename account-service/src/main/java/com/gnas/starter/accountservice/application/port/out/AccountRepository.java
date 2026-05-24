package com.gnas.starter.accountservice.application.port.out;

import com.gnas.starter.accountservice.domain.Account;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountRepository {
    Account save(Account account);
    Optional<Account> findById(Long id);
    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<Account> findByCustomerId(String customerId);
    boolean existsByAccountNumber(String accountNumber);
    boolean existsByCustomerId(String customerId);
    void deposit(BigDecimal money, String customerId);
    void withdraw(BigDecimal money, String customerId);
}
