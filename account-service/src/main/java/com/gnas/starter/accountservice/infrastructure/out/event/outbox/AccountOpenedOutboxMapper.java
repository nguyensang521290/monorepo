package com.gnas.starter.accountservice.infrastructure.out.event.outbox;

import com.gnas.account.key.AccountKey;
import com.gnas.account.payload.AccountPayload;
import com.gnas.starter.accountservice.domain.Account;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;

@Component
public class AccountOpenedOutboxMapper implements OutboxMapper<Account> {

    @Override
    public SpecificRecord toKey(Account input) {
        return AccountKey.newBuilder()
                .setId(input.getId().toString())
                .build();
    }

    @Override
    public SpecificRecord toPayload(Account input) {
        return AccountPayload.newBuilder()
                .setId(input.getId().toString())
                .setUserId(input.getUserId().toString())
                .setBalance(input.getBalance().doubleValue())
                .build();
    }
}
