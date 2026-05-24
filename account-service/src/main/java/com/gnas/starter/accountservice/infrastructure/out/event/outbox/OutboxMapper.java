package com.gnas.starter.accountservice.infrastructure.out.event.outbox;

import org.apache.avro.specific.SpecificRecord;

public interface OutboxMapper<Input> {
    SpecificRecord toKey(Input input);
    SpecificRecord toPayload(Input input);
}
