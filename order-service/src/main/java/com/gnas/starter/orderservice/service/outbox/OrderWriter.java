package com.gnas.starter.orderservice.service.outbox;

import com.gnas.order.OrderCreatedEvent;
import com.gnas.order.OrderIdKey;
import com.gnas.starter.orderservice.domain.OrderData;
import com.gnas.starter.orderservice.repository.OutboxEventJpaRepository;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderWriter extends AbstractOutboxWriter<OrderData, OrderIdKey, OrderCreatedEvent> {
    public OrderWriter(
            OutboxEventJpaRepository outboxEventJpaRepository,
            @Qualifier("keySerializer") KafkaAvroSerializer keySerializer,
            @Qualifier("valueSerializer") KafkaAvroSerializer valueSerializer) {
        super(outboxEventJpaRepository, keySerializer, valueSerializer);
    }

    @Override
    public WriterType getType() {
        return WriterType.ORDER_WRITER;
    }

    @Override
    protected OrderIdKey buildKey(OrderData data) {
        return OrderIdKey.newBuilder().setOrderId(data.getOrderId().toString()).build();
    }

    @Override
    protected OrderCreatedEvent buildPayload(OrderData data) {
        return OrderCreatedEvent.newBuilder()
                .setOrderId(data.getOrderId().toString())
                .setStatus(data.getStatus().toString())
                .setProducts(data.getProducts())
                .setCreatedAt(data.getCreatedAt() != null ? data.getCreatedAt().toString() : LocalDateTime.now().toString())
                .build();
    }
}
