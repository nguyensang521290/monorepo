package com.gnas.starter.accountservice.infrastructure.out.event.outbox;

public interface OutboxWriter<Input> {
    String getType();
    void write(Input input);
}
