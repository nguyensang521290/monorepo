package com.gnas.starter.identityservice.infrastructure.out.event.outbox;

import com.gnas.starter.identityservice.infrastructure.out.persistence.jpa.UserJpaEntity;
import com.gnas.starter.outbox.service.OutboxService;
import org.springframework.stereotype.Service;

@Service
public class UserRegisteredOutboxWriter extends AbstractOutboxWriter<UserJpaEntity> {

    private final UserRegisteredOutboxMapper mapper;
    private static final String USER_REGISTERED_TOPIC = "user-registered-topic";

    public UserRegisteredOutboxWriter(
            OutboxService outboxService,
            UserRegisteredOutboxMapper mapper) {
        super(outboxService);
        this.mapper = mapper;
    }

    @Override
    public String getType() {
        return "user_registered_writer";
    }

    @Override
    protected OutboxMapper<UserJpaEntity> getMapper() {
        return mapper;
    }

    @Override
    protected String getTopic() {
        return USER_REGISTERED_TOPIC;
    }
}
