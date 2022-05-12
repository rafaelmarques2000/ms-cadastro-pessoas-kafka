package com.rafa.kafkaestudo;

import com.rafa.kafkaestudo.infra.kafka.consumer.KafkaPersonConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class KafkaEstudoApplication {
	public static void main(String[] args) {
		SpringApplication.run(KafkaEstudoApplication.class, args);
	}
}
