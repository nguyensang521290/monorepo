package com.gnas.starter.identityservice.infrastructure.out.event.outbox;

import com.gnas.starter.identityservice.infrastructure.out.persistence.jpa.UserJpaEntity;
import com.gnas.user.key.UserKey;
import com.gnas.user.payload.UserRegisteredPayload;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;


@Component
public class UserRegisteredOutboxMapper implements OutboxMapper<UserJpaEntity> {

    @Override
    public SpecificRecord toKey(UserJpaEntity user) {
        return UserIdKey.newBuilder()
                .setUserId(user.getId())
                .build();
    }

    @Override
    public SpecificRecord toPayload(UserJpaEntity user) {
        return UserRegisteredEvent.newBuilder()
                .setUserId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .build();
    }
}
