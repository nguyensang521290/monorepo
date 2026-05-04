package com.gnas.starter.orderservice.service.outbox;

public interface OutboxWriter<Input> {
    WriterType getType();

    void write(Input input);
}
