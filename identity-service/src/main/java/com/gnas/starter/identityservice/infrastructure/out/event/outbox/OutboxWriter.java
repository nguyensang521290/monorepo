package com.gnas.starter.identityservice.infrastructure.out.event.outbox;

public interface OutboxWriter<Input> {
    String getType();
    void write(Input input);
}
