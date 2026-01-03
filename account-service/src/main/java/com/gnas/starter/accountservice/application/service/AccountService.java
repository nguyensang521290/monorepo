package com.gnas.starter.accountservice.application.service;

import com.gnas.starter.accountservice.application.port.out.AccountEventPublisher;
import com.gnas.starter.accountservice.application.port.out.AccountRepository;
import com.gnas.starter.accountservice.domain.Account;
import com.gnas.starter.accountservice.domain.event.AccountOpenedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;
    private final AccountEventPublisher eventPublisher;

    @Transactional
    public Account openAccount(String customerId, String currency) {
        // 1. Create account
        Account newAccount = Account.openAccount(customerId, accountNumberGenerator.generate(), currency);

        // 2. Persist account
        Account savedAccount = accountRepository.save(newAccount);

        // 3. Publish domain events
        eventPublisher.publishAccountOpened(new AccountOpenedEvent(
                savedAccount.getId(),
                savedAccount.getAccountNumber(),
                savedAccount.getCustomerId(),
                savedAccount.getCurrency(),
                savedAccount.getCreatedAt()
        ));

        return savedAccount;
    }
}