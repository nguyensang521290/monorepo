package com.gnas.starter.orderservice.service.outbox;

import org.apache.avro.specific.SpecificRecord;

public interface OutboxMapper<Input> {
    SpecificRecord toKey(Input input);
    SpecificRecord toPayload(Input input);
}
