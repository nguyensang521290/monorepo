package com.gnas.starter.transactionservice.dto;

import java.math.BigDecimal;

public record DepositRequest(Long accountId, BigDecimal amount) {}
