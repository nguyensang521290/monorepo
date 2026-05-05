package com.gnas.starter.notificationservice.infrastructure.in.messaging;

import com.gnas.account.AccountOpenedEvent;
import com.gnas.account.AccountIdKey;
import com.gnas.order.OrderCreatedEvent;
import com.gnas.order.OrderIdKey;
import com.gnas.starter.notificationservice.application.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventListener {

    private final EmailService emailService;

    @KafkaListener(topics = "account-topic", groupId = "notification-group-id")
    public void handleAccountOpened(ConsumerRecord<AccountIdKey, AccountOpenedEvent> record) {
        AccountOpenedEvent event = record.value();
        String customerId = event.getCustomerId();
        String accountNumber = event.getAccountNumber();
        
        log.info("Processing AccountOpenedEvent for customer: {}", customerId);
        
        // In a real app, you'd look up the customer's email from the Identity/Customer service
        String dummyEmail = customerId.toLowerCase() + "@example.com";
        
        String subject = "Account Successfully Opened";
        String body = String.format("Dear %s,\n\nYour account %s has been successfully opened.\n\nThank you for choosing GNAS Bank!", 
            customerId, accountNumber);
        
        emailService.sendSimpleMessage(dummyEmail, subject, body);
    }

    @KafkaListener(topics = "order-topic", groupId = "notification-group-id")
    public void handleOrderCreated(ConsumerRecord<OrderIdKey, OrderCreatedEvent> record) {
        OrderCreatedEvent event = record.value();
        String orderId = event.getOrderId();
        
        log.info("Processing OrderCreatedEvent for order: {}", orderId);
        
        String dummyEmail = "customer@example.com";
        String subject = "Order Confirmation - " + orderId;
        String body = String.format("Hello,\n\nYour order %s has been created with status: %s.\n\nBest regards,\nGNAS Shop", 
            orderId, event.getStatus());
        
        emailService.sendSimpleMessage(dummyEmail, subject, body);
    }
}
