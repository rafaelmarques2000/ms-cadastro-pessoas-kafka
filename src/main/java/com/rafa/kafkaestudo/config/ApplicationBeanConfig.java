package com.rafa.kafkaestudo.config;

import com.rafa.kafkaestudo.domain.person.ports.PersonRepositoryPort;
import com.rafa.kafkaestudo.domain.person.ports.PersonServicePort;
import com.rafa.kafkaestudo.domain.ports.QueueProducerPort;
import com.rafa.kafkaestudo.domain.person.service.PersonServiceImplPort;
import com.rafa.kafkaestudo.infra.database.mysql.repository.PersonRepository;
import com.rafa.kafkaestudo.infra.kafka.consumer.KafkaPersonConsumer;
import com.rafa.kafkaestudo.infra.kafka.producer.KafkaProduceImplPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
public class ApplicationBeanConfig {

    @Bean
    public PersonRepositoryPort personRepositoryPort(DatabaseClient databaseClient) {
        return new PersonRepository(databaseClient);
    }

    @Bean
    public PersonServicePort personServicePort(PersonRepositoryPort personRepositoryPort, QueueProducerPort queueProducerPort) {
        return new PersonServiceImplPort(personRepositoryPort, queueProducerPort);
    }

    @Bean
    public QueueProducerPort queueProducerPort() {
        return new KafkaProduceImplPort();
    }

    @Bean
    public KafkaPersonConsumer kafkaPersonConsumer(PersonServicePort personServicePort) {
        return new KafkaPersonConsumer(personServicePort);
    }
}
