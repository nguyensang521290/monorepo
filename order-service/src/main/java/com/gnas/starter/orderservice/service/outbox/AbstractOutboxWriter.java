package com.gnas.starter.orderservice.service.outbox;

import com.gnas.starter.orderservice.domain.OutboxEvent;
import com.gnas.starter.orderservice.repository.OutboxEventJpaRepository;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractOutboxWriter<Input, Key, Payload> implements OutboxWriter<Input> {
    private final OutboxEventJpaRepository outboxEventJpaRepository;
    private final KafkaAvroSerializer keySerializer;
    private final KafkaAvroSerializer valueSerializer;

    @Override
    public void write(Input input) {
        String eventType = getType().getValue();
        Key builtKey = buildKey(input);
        Payload builtPayload = buildPayload(input);

        OutboxEvent orderedEvent = OutboxEvent.builder()
                .eventType(eventType)
                .key(keySerializer.serialize("order-topic", builtKey))
                .payload(valueSerializer.serialize("order-topic", builtPayload))
                .build();

        outboxEventJpaRepository.save(orderedEvent);
    }

    protected abstract Key buildKey(Input input);

    protected abstract Payload buildPayload(Input input);
}
