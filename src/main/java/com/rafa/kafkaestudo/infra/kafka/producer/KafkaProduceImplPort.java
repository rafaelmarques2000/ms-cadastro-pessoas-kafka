package com.rafa.kafkaestudo.infra.kafka.producer;

import com.rafa.kafkaestudo.domain.ports.QueueProducerPort;
import com.rafa.kafkaestudo.infra.kafka.exception.KafkaProducerException;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaProduceImplPort implements QueueProducerPort {

    @Override
    public void sendMessage(String queueName, String message, String index) {

    }

    @Override
    public void sendMessage(String queueName, String message) {

        sendKakfaMessage(queueName, message);
    }

    private void sendKakfaMessage(String queueName, String message) {

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties());
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(queueName, message);
        Callback callback = (data, ex) -> {
           if(ex != null) {
              throw new KafkaProducerException(ex.getMessage());
           }
           System.out.println("Mensagem produzida com sucesso");
        };

        try {
            producer.send(producerRecord, callback).get();
        }catch (InterruptedException | ExecutionException exception) {
            throw new KafkaProducerException(exception.getMessage());
        }
    }

    private Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.MAX_BLOCK_MS_CONFIG, "10000");

        return properties;
    }
}
