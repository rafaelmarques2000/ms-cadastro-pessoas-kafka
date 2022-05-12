package com.rafa.kafkaestudo.infra.exception;

public abstract class InfraException extends RuntimeException {
    public InfraException(String message) {
        super(message);
    }
}
