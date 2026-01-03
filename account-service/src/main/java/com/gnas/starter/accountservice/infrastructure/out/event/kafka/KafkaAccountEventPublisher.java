package com.gnas.starter.accountservice.infrastructure.out.event.kafka;

import com.gnas.starter.accountservice.application.port.out.AccountEventPublisher;
import com.gnas.starter.accountservice.domain.event.AccountOpenedEvent;
import org.springframework.stereotype.Component;

@Component
public class KafkaAccountEventPublisher implements AccountEventPublisher {
    @Override
    public void publishAccountOpened(AccountOpenedEvent event) {
        // TODO: publish account opened event.
    }
}
