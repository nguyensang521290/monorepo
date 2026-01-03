package com.gnas.starter.accountservice.infrastructure.out.persistence.jpa;

import com.gnas.starter.accountservice.application.port.out.AccountRepository;
import com.gnas.starter.accountservice.domain.Account;
import com.gnas.starter.accountservice.infrastructure.out.persistence.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
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
}