package com.gnas.starter.accountservice.infrastructure.out.event.outbox;

import com.gnas.account.deposited.payload.AccountDepositedPayload;
import com.gnas.account.key.AccountKey;
import com.gnas.starter.accountservice.domain.AccountDepositData;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;

@Component
public class AccountDepositedOutboxMapper implements OutboxMapper<AccountDepositData> {

    @Override
    public SpecificRecord toKey(AccountDepositData input) {
        return AccountKey.newBuilder()
                .setId(input.account().getId().toString())
                .build();
    }

    @Override
    public SpecificRecord toPayload(AccountDepositData input) {
        return AccountDepositedPayload.newBuilder()
                .setAccountId(input.account().getId().toString())
                .setAmount(input.amount().doubleValue())
                .setDepositedAt(input.depositedAt().toString())
                .build();
    }
}
