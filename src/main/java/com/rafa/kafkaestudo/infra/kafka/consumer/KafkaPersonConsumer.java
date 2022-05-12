package com.rafa.kafkaestudo.infra.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafa.kafkaestudo.domain.person.ports.PersonServicePort;
import com.rafa.kafkaestudo.infra.kafka.consumer.mapper.PersonMessageMapper;
import com.rafa.kafkaestudo.infra.kafka.consumer.model.PersonMessageModel;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaPersonConsumer {

     private PersonServicePort personServicePort;

     public KafkaPersonConsumer(PersonServicePort personServicePort) {
         this.personServicePort = personServicePort;
     }

    public void consumer() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties());
        consumer.subscribe(Collections.singletonList("cadastro_pessoas"));

        while(true) {
            var records = consumer.poll(Duration.ofMillis(100));
            for(ConsumerRecord<String, String> record: records) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    PersonMessageModel person = objectMapper.readValue(record.value(), PersonMessageModel.class);

                    this.personServicePort
                            .createPerson(PersonMessageMapper.toPersonModel(person))
                            .mapNotNull(personId -> {
                                System.out.println(personId);
                                return null;
                            }).subscribe();

                }catch (JsonProcessingException jsonProcessingException){
                    System.out.println(jsonProcessingException.getMessage());
                }
            }
        }

    }


    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "person");
        return properties;
    }
}
