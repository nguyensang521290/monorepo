package com.gnas.starter.accountservice.infrastructure.out.event.kafka;

import com.gnas.account.AccountIdKey;
import com.gnas.account.AccountOpenedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventPublisher {
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public void publish(AccountOpenedEvent event) {
        log.info("Publishing AccountOpenedEvent to Kafka: {}", event);
        AccountIdKey key = AccountIdKey.newBuilder()
                .setAccountId(event.getAccountId())
                .build();
        
        kafkaTemplate.send("account-topic", key, event);
    }
}
