package com.gnas.starter.accountservice.runner;

import com.gnas.starter.accountservice.application.port.out.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Component
@Order(2)
@RequiredArgsConstructor
@Slf4j
public class UpdateAccountBalanceRunner implements CommandLineRunner {
    private final AccountRepository accountRepository;

    private final String CUSTOMER_ID = "c496223a-e33f-48ba-ad19-67fdf900fe3b";
    private final BigDecimal DEPOSITING_MONEY = BigDecimal.valueOf(1000);
    private final BigDecimal WITHDRAWING_MONEY = BigDecimal.valueOf(800);
    private final BigDecimal TRANSFERRING_MONEY = BigDecimal.valueOf(400);

    @Override
    public void run(String... args) throws Exception {
        CompletableFuture<Void> withdrawProcess = CompletableFuture.runAsync(() -> {
            accountRepository.withdraw(WITHDRAWING_MONEY, CUSTOMER_ID);
        });

        CompletableFuture<Void> transferProcess = CompletableFuture.runAsync(() -> {
            accountRepository.withdraw(TRANSFERRING_MONEY, CUSTOMER_ID);
        });

        CompletableFuture.allOf(withdrawProcess, transferProcess).join();
    }
}
