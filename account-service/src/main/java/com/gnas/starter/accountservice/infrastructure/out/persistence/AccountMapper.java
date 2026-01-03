package com.gnas.starter.accountservice.infrastructure.out.persistence;

import com.gnas.starter.accountservice.domain.Account;
import com.gnas.starter.accountservice.infrastructure.out.persistence.jpa.AccountJpaEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountMapper {
  public static Account toDomain(AccountJpaEntity e) {
        if (e == null) return null;
        return Account.reconstitute(e.getId(), e.getCustomerId(), e.getAccountNumber(), e.getCurrency(), e.getStatus(), e.getCreatedAt());
    }

    public static AccountJpaEntity toEntity(Account a) {
        if (a == null) return null;

        AccountJpaEntity entity = new AccountJpaEntity();
        entity.setCustomerId(a.getCustomerId());
        entity.setAccountNumber(a.getAccountNumber());
        entity.setCurrency(a.getCurrency());
        entity.setStatus(a.getStatus());
        return entity;
    }
}