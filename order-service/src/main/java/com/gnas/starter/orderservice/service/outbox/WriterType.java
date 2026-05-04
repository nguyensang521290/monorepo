package com.gnas.starter.orderservice.service.outbox;

import lombok.Getter;

@Getter
public enum WriterType {
    ORDER_WRITER("order__writer"),
    ;

    private final String value;

    WriterType(String val) {
        this.value = val;
    }
}
