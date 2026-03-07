package com.gnas.starter.transactionservice.repository;

import com.gnas.starter.transactionservice.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
