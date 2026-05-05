package com.gnas.starter.accountservice.application.service;

import com.gnas.account.AccountOpenedEvent;
import com.gnas.starter.accountservice.application.port.out.AccountRepository;
import com.gnas.starter.accountservice.domain.Account;
import com.gnas.starter.accountservice.infrastructure.out.event.kafka.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;
    private final KafkaEventPublisher eventPublisher;

    @Transactional
    public Account openAccount(String customerId, String currency) {
        Account newAccount = Account.openAccount(customerId, accountNumberGenerator.generate(), currency);
        Account savedAccount = accountRepository.save(newAccount);

        publishAccountOpenedEvent(savedAccount);
        
        return savedAccount;
    }

    private void publishAccountOpenedEvent(Account account) {
        AccountOpenedEvent event = AccountOpenedEvent.newBuilder()
                .setAccountId(account.getId())
                .setAccountNumber(account.getAccountNumber())
                .setCustomerId(account.getCustomerId())
                .setCurrency(account.getCurrency())
                .setCreatedAt(account.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
        
        eventPublisher.publish(event);
    }

    @Transactional
    public Account deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));
        
        account.deposit(amount);
        return accountRepository.save(account);
    }
}
