package com.gnas.starter.sharedlib.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCompletedEvent {
    private String transactionId;
    private Long accountId;
    private BigDecimal amount;
    private String type; // e.g., DEPOSIT
    private String status; // COMPLETED
    private LocalDateTime timestamp;
}
