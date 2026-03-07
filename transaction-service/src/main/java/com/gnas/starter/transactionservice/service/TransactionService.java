package com.gnas.starter.transactionservice.service;

import com.gnas.starter.sharedlib.event.TransactionCompletedEvent;
import com.gnas.starter.transactionservice.domain.Transaction;
import com.gnas.starter.transactionservice.dto.DepositRequest;
import com.gnas.starter.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RestClient restClient = RestClient.builder().baseUrl("http://localhost:8080/account-service").build();

    @Transactional
    public Transaction deposit(DepositRequest request) {
        // 1. PATCH account-service
        restClient.patch()
                .uri("/v1/accounts/{id}/balance?amount={amount}", request.accountId(), request.amount())
                .retrieve()
                .toBodilessEntity();

        // 2. Save transaction
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID().toString())
                .accountId(request.accountId())
                .amount(request.amount())
                .type("DEPOSIT")
                .status("COMPLETED")
                .createdAt(LocalDateTime.now())
                .build();
        
        Transaction savedTransaction = transactionRepository.save(transaction);

        // 3. Publish event
        TransactionCompletedEvent event = TransactionCompletedEvent.builder()
                .transactionId(savedTransaction.getId())
                .accountId(savedTransaction.getAccountId())
                .amount(savedTransaction.getAmount())
                .type(savedTransaction.getType())
                .status(savedTransaction.getStatus())
                .timestamp(savedTransaction.getCreatedAt())
                .build();
        
        kafkaTemplate.send("transaction-completed", event.getTransactionId(), event);
        
        return savedTransaction;
    }
}
