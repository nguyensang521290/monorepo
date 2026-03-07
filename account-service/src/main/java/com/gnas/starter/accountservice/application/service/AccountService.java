package com.gnas.starter.accountservice.application.service;

import com.gnas.starter.accountservice.application.port.out.AccountEventPublisher;
import com.gnas.starter.accountservice.application.port.out.AccountRepository;
import com.gnas.starter.accountservice.domain.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;
    private final AccountEventPublisher eventPublisher;

    @Transactional
    public Account openAccount(String customerId, String currency) {
        Account newAccount = Account.openAccount(customerId, accountNumberGenerator.generate(), currency);
        return accountRepository.save(newAccount);
    }

    @Transactional
    public Account deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));
        
        account.deposit(amount);
        return accountRepository.save(account);
    }
}
