package com.rafa.kafkaestudo.domain.ports;

public interface QueueProducerPort {
    void sendMessage( String queueName,String message,String index);
    void sendMessage( String queueName,String message);
}
