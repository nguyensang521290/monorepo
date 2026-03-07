package com.gnas.starter.notificationservice.listener;

import com.gnas.starter.sharedlib.event.TransactionCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionCompletedListener {

    @KafkaListener(topics = "transaction-completed", groupId = "notification-service-group")
    public void handle(TransactionCompletedEvent event) {
        if ("DEPOSIT".equals(event.getType())) {
            log.info("Creating 'deposit success' notification for account: {}, amount: {}", 
                    event.getAccountId(), event.getAmount());
            // Logic to send notification (SMS, Email, etc.) would go here
        }
    }
}
