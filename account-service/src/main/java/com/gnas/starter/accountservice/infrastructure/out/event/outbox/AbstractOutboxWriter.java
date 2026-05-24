package com.gnas.starter.accountservice.infrastructure.out.event.outbox;

import com.gnas.starter.outbox.service.OutboxService;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecord;

@RequiredArgsConstructor
public abstract class AbstractOutboxWriter<Input> implements OutboxWriter<Input> {
    private final OutboxService outboxService;

    @Override
    public void write(Input input) {
        String eventType = getType();
        String topic = getTopic();
        SpecificRecord key = getMapper().toKey(input);
        SpecificRecord payload = getMapper().toPayload(input);

        outboxService.pushEventToTable(topic, key, payload, eventType);
    }

    protected abstract OutboxMapper<Input> getMapper();
    protected abstract String getTopic();
}
