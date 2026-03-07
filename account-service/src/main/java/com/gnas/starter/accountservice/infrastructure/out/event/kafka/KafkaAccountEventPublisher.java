package com.gnas.starter.accountservice.infrastructure.out.event.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnas.starter.accountservice.application.port.out.AccountEventPublisher;
import com.gnas.starter.accountservice.domain.event.AccountOpenedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaAccountEventPublisher implements AccountEventPublisher {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publishAccountOpened(AccountOpenedEvent event) {
        String key = event.customerId();
        byte[] payload;

        try {
            payload = objectMapper.writeValueAsBytes(event);

            kafkaTemplate.send("account-opened", event.customerId(), payload)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Published account-opened event for id={}", key);
                        } else {
                            log.error("Failed to publish account-opened event for id={}", key, ex);
                        }
                    });
        } catch (JsonProcessingException e) {
            log.error("Cannot serialize payload for key={}", key);
        }
    }
}
