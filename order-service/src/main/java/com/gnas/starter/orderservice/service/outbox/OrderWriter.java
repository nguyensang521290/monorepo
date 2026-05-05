package com.gnas.starter.orderservice.service.outbox;

import com.gnas.starter.orderservice.domain.OrderData;
import com.gnas.starter.orderservice.repository.OutboxEventJpaRepository;
import com.gnas.starter.orderservice.service.mapper.OrderMapper;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderWriter extends AbstractOutboxWriter<OrderData> {
    private final OrderMapper orderMapper;

    public OrderWriter(
            OutboxEventJpaRepository outboxEventJpaRepository,
            @Qualifier("keySerializer") KafkaAvroSerializer keySerializer,
            @Qualifier("valueSerializer") KafkaAvroSerializer valueSerializer,
            OrderMapper orderMapper) {
        super(outboxEventJpaRepository, keySerializer, valueSerializer);
        this.orderMapper = orderMapper;
    }

    @Override
    public WriterType getType() {
        return WriterType.ORDER_WRITER;
    }

    @Override
    protected OutboxMapper<OrderData> getMapper() {
        return orderMapper;
    }
}
