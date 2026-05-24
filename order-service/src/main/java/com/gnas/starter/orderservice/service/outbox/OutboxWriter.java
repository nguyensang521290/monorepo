package com.gnas.starter.orderservice.service.outbox;

public interface OutboxWriter<Input> {
    String getType();
    void write(Input input);
}
