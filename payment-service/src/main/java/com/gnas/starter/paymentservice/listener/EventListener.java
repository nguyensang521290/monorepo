package com.gnas.starter.paymentservice.listener;

import com.gnas.order.OrderCreatedEvent;
import com.gnas.order.OrderIdKey;
import com.gnas.starter.paymentservice.service.OrderPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventListener {
    private final OrderPaymentService service;

    @KafkaListener(topics = "order-topic", groupId = "payment-group-id")
    public void listen(ConsumerRecord<OrderIdKey, OrderCreatedEvent> record, Acknowledgment acknowledgment) {
        try {
            log.info("EventListener::listen consumed kafka message with key={}", record.key());

            if (record.value() == null) {
                log.info("EventListener::listen record value is NULL");
                return;
            }

            OrderCreatedEvent event = record.value();
            service.processOrderPayment(UUID.fromString(event.getOrderId()), event.getProducts());

        } catch (Exception exception) {
            log.error("EventListener::listen Handle consuming kafka message failed with error={}", exception.getMessage(), exception);
        } finally {
            acknowledgment.acknowledge();
        }
    }
}
