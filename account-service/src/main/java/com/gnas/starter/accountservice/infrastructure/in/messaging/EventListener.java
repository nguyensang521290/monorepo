package com.gnas.starter.accountservice.infrastructure.in.messaging;

import com.gnas.order.OrderCreatedEvent;
import com.gnas.order.OrderIdKey;
import com.gnas.starter.accountservice.application.service.AccountService;
import com.gnas.user.UserIdKey;
import com.gnas.user.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventListener {
    private final AccountService accountService;

    @KafkaListener(topics = "order-topic", groupId = "account-group-id")
    public void listen(ConsumerRecord<OrderIdKey, OrderCreatedEvent> record, Acknowledgment acknowledgment) {
        try {
            log.info("EventListener::listen consumed kafka message with key={}", record.key());

            if (record.value() == null) {
                log.info("EventListener::listen record value is NULL");
                return;
            }

            OrderCreatedEvent event = record.value();

            log.info("SUCCESSFULLY RECEIVED event: {}", event);

        } catch (Exception exception) {
            log.error("EventListener::listen Handle consuming kafka message failed with error={}", exception.getMessage(), exception);
        } finally {
            acknowledgment.acknowledge();
        }
    }

    @KafkaListener(topics = "user-registered-topic", groupId = "account-group-id")
    public void listenUserRegistered(ConsumerRecord<UserIdKey, UserRegisteredEvent> record, Acknowledgment acknowledgment) {
        try {
            log.info("EventListener::listenUserRegistered consumed kafka message with key={}", record.key());

            if (record.value() == null) {
                log.info("EventListener::listenUserRegistered record value is NULL");
                return;
            }

            UserRegisteredEvent event = record.value();
            log.info("SUCCESSFULLY RECEIVED UserRegisteredEvent: {}", event);

            // Create account for the new user. Default currency to USD for now.
            accountService.openAccount(String.valueOf(event.getUserId()), "USD");
            log.info("Successfully opened account for customerId={}", event.getUserId());

        } catch (Exception exception) {
            log.error("EventListener::listenUserRegistered Handle consuming kafka message failed with error={}", exception.getMessage(), exception);
        } finally {
            acknowledgment.acknowledge();
        }
    }
}
