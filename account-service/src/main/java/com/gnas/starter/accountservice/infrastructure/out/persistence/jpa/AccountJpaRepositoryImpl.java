package com.gnas.starter.accountservice.infrastructure.out.persistence.jpa;

import com.gnas.starter.accountservice.application.port.out.AccountRepository;
import com.gnas.starter.accountservice.domain.Account;
import com.gnas.starter.accountservice.infrastructure.out.persistence.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AccountJpaRepositoryImpl implements AccountRepository {
    private final SpringDataAccountRepository repository;

    @Override
    public Account save(Account account) {
        AccountJpaEntity entity = AccountMapper.toEntity(account);
        AccountJpaEntity saved = repository.save(entity);
        return AccountMapper.toDomain(saved);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return repository.findById(id).map(AccountMapper::toDomain);
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber).map(AccountMapper::toDomain);
    }

    @Override
    public boolean existsByAccountNumber(String accountNumber) {
        return repository.existsByAccountNumber(accountNumber);
    }

    @Override
    public void deposit(BigDecimal money, String customerId) {
        AccountJpaEntity account = repository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("No account found"));

        account.setBalance(account.getBalance().add(money));
        repository.save(account);
        log.info("deposit successfully with account number={}, money={}", account.getAccountNumber(), money);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void withdraw(BigDecimal money, String customerId) {
        AccountJpaEntity account = repository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("No account found"));

        if (account.getBalance().compareTo(money) >= 0) {
            repository.withdraw(money, customerId);
        } else {
            String errorMessage = String.format("Account=%s is not enough balance for performing", account.getAccountNumber());
            throw new RuntimeException(errorMessage);
        }

        log.info("withdraw successfully with account number={}, money={}", account.getAccountNumber(), money);
    }
}