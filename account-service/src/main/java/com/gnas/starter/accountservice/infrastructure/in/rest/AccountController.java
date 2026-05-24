package com.gnas.starter.accountservice.infrastructure.in.rest;

import com.gnas.starter.accountservice.application.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/v1/accounts")
    public ResponseEntity<OpenAccountResponse> openBankAccount(@Valid @RequestBody OpenAccountRequest req) {
        var account = accountService.openAccount(req.customerId(), req.currency());
        var resp = AccountMapper.toResponse(account);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/v1/accounts/customer/{customerId}")
    public ResponseEntity<OpenAccountResponse> getAccountByCustomerId(@PathVariable String customerId) {
        var account = accountService.findByCustomerId(customerId);
        return ResponseEntity.ok(AccountMapper.toResponse(account));
    }

    @PostMapping("/v1/accounts/{id}/deposit")
    public ResponseEntity<BalanceUpdateResponse> deposit(@PathVariable Long id, @Valid @RequestBody DepositRequest req) {
        var account = accountService.deposit(id, req.amount());
        return ResponseEntity.ok(new BalanceUpdateResponse(account.getId(), account.getBalance()));
    }

    @PatchMapping("/v1/accounts/{id}/balance")
    public ResponseEntity<BalanceUpdateResponse> updateBalance(@PathVariable Long id, @RequestParam BigDecimal amount) {
        var account = accountService.deposit(id, amount);
        return ResponseEntity.ok(new BalanceUpdateResponse(account.getId(), account.getBalance()));
    }

    @DeleteMapping("/v1/accounts/{id}")
    public ResponseEntity<Void> closeAccount(@PathVariable Long id) {
        accountService.closeAccount(id);
        return ResponseEntity.noContent().build();
    }
}
