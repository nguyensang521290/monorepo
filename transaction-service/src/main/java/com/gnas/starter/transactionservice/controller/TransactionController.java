package com.gnas.starter.transactionservice.controller;

import com.gnas.starter.transactionservice.dto.DepositRequest;
import com.gnas.starter.transactionservice.dto.DepositResponse;
import com.gnas.starter.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/v1/transactions/deposit")
    public ResponseEntity<DepositResponse> deposit(@RequestBody DepositRequest request) {
        var transaction = transactionService.deposit(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new DepositResponse(transaction.getId(), transaction.getStatus()));
    }
}
