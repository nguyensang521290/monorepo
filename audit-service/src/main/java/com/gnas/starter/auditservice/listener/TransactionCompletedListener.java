package com.gnas.starter.auditservice.listener;

import com.gnas.starter.sharedlib.event.TransactionCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionCompletedListener {

    @KafkaListener(topics = "transaction-completed", groupId = "audit-service-group")
    public void handle(TransactionCompletedEvent event) {
        log.info("Saving audit log for transaction: {}, type: {}, account: {}", 
                event.getTransactionId(), event.getType(), event.getAccountId());
        // Logic to persist audit log into DB would go here
    }
}
