package com.gnas.starter.accountservice.infrastructure.out.event.outbox;

import com.gnas.starter.accountservice.domain.Account;
import com.gnas.starter.outbox.service.OutboxService;
import org.springframework.stereotype.Service;

@Service
public class AccountOpenedOutboxWriter extends AbstractOutboxWriter<Account> {

    private final AccountOpenedOutboxMapper mapper;
    private static final String ACCOUNT_TOPIC = "account-topic";

    public AccountOpenedOutboxWriter(
            OutboxService outboxService,
            AccountOpenedOutboxMapper mapper) {
        super(outboxService);
        this.mapper = mapper;
    }

    @Override
    public String getType() {
        return "account_opened_writer";
    }

    @Override
    protected OutboxMapper<Account> getMapper() {
        return mapper;
    }

    @Override
    protected String getTopic() {
        return ACCOUNT_TOPIC;
    }
}
