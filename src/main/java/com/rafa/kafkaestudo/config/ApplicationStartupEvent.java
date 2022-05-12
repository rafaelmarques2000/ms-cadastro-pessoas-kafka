package com.rafa.kafkaestudo.config;

import com.rafa.kafkaestudo.infra.kafka.consumer.KafkaPersonConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupEvent implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    KafkaPersonConsumer kafkaPersonConsumer;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
         kafkaPersonConsumer.consumer();
    }
}
