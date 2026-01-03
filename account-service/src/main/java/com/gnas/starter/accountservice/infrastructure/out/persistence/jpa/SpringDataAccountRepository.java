package com.gnas.starter.accountservice.infrastructure.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataAccountRepository extends JpaRepository<AccountJpaEntity, Long> {
    Optional<AccountJpaEntity> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);
}