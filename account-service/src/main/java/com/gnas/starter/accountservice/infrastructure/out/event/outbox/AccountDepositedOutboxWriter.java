package com.gnas.starter.accountservice.infrastructure.out.event.outbox;

import com.gnas.starter.accountservice.domain.AccountDepositData;
import com.gnas.starter.outbox.service.OutboxService;
import org.springframework.stereotype.Service;

@Service
public class AccountDepositedOutboxWriter extends AbstractOutboxWriter<AccountDepositData> {

    private final AccountDepositedOutboxMapper mapper;
    private static final String ACCOUNT_TOPIC = "account-topic";

    public AccountDepositedOutboxWriter(
            OutboxService outboxService,
            AccountDepositedOutboxMapper mapper) {
        super(outboxService);
        this.mapper = mapper;
    }

    @Override
    public String getType() {
        return "account_deposited_writer";
    }

    @Override
    protected OutboxMapper<AccountDepositData> getMapper() {
        return mapper;
    }

    @Override
    protected String getTopic() {
        return ACCOUNT_TOPIC;
    }
}
