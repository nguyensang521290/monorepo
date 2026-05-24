package com.gnas.starter.accountservice.application.service;

import com.gnas.starter.accountservice.application.port.out.AccountRepository;
import com.gnas.starter.accountservice.domain.Account;
import com.gnas.starter.accountservice.domain.AccountDepositData;
import com.gnas.starter.accountservice.infrastructure.out.event.outbox.AccountDepositedOutboxWriter;
import com.gnas.starter.accountservice.infrastructure.out.event.outbox.AccountOpenedOutboxWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;
    private final AccountOpenedOutboxWriter outboxWriter;
    private final AccountDepositedOutboxWriter depositOutboxWriter;

    @Transactional
    public Account openAccount(String customerId, String currency) {
        return accountRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    Account newAccount = Account.openAccount(customerId, accountNumberGenerator.generate(), currency);
                    Account savedAccount = accountRepository.save(newAccount);
                    outboxWriter.write(savedAccount);
                    return savedAccount;
                });
    }

    @Transactional(readOnly = true)
    public Account findByCustomerId(String customerId) {
        return accountRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found for customerId: " + customerId));
    }

    @Transactional
    public Account deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));
        
        account.deposit(amount);
        Account savedAccount = accountRepository.save(account);
        
        depositOutboxWriter.write(new AccountDepositData(savedAccount, amount, LocalDateTime.now()));
        
        return savedAccount;
    }

    @Transactional
    public void closeAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));
        account.close();
        accountRepository.save(account);
    }
}
