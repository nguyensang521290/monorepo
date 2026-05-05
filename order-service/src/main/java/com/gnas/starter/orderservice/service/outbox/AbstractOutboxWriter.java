package com.gnas.starter.orderservice.service.outbox;

import com.gnas.starter.orderservice.domain.OutboxEvent;
import com.gnas.starter.orderservice.repository.OutboxEventJpaRepository;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecord;

@RequiredArgsConstructor
public abstract class AbstractOutboxWriter<Input> implements OutboxWriter<Input> {
    private final OutboxEventJpaRepository outboxEventJpaRepository;
    private final KafkaAvroSerializer keySerializer;
    private final KafkaAvroSerializer valueSerializer;

    @Override
    public void write(Input input) {
        String eventType = getType().getValue();
        SpecificRecord builtKey = getMapper().toKey(input);
        SpecificRecord builtPayload = getMapper().toPayload(input);

        OutboxEvent orderedEvent = OutboxEvent.builder()
                .eventType(eventType)
                .key(keySerializer.serialize("order-topic", builtKey))
                .payload(valueSerializer.serialize("order-topic", builtPayload))
                .build();

        outboxEventJpaRepository.save(orderedEvent);
    }

    protected abstract OutboxMapper<Input> getMapper();
}
