package com.gnas.starter.accountservice.infrastructure.out.persistence.jpa;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

public interface SpringDataAccountRepository extends JpaRepository<AccountJpaEntity, Long> {
    Optional<AccountJpaEntity> findByAccountNumber(String accountNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<AccountJpaEntity> findByCustomerId(String customerId);

    boolean existsByAccountNumber(String accountNumber);

    boolean existsByCustomerId(String customerId);

    @Modifying
    @Query("""
        UPDATE ACCOUNT a
        SET a.balance = a.balance - :money
        WHERE a.customerId = :customerId
    """)
    void withdraw(BigDecimal money, String customerId);
}