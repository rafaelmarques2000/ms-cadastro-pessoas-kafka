package com.rafa.kafkaestudo.infra.kafka.exception;

import com.rafa.kafkaestudo.infra.exception.InfraException;

public class KafkaProducerException extends InfraException {
    public KafkaProducerException(String message) {
        super(message);
    }
}
