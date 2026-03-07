package com.gnas.starter.transactionservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    private String id;
    private Long accountId;
    private BigDecimal amount;
    private String type; // DEPOSIT
    private String status; // COMPLETED
    private LocalDateTime createdAt;
}
