package com.gnas.starter.orderservice.exception;

public class DeserializationException extends RuntimeException {
    public DeserializationException() {
        super();
    }
    public DeserializationException(String message) {
        super(message);
    }
    public DeserializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
