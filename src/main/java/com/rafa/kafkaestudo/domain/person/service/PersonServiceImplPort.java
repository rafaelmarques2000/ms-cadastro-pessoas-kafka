package com.rafa.kafkaestudo.domain.person.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafa.kafkaestudo.domain.person.exception.PersonNotFoundException;
import com.rafa.kafkaestudo.domain.person.model.PersonModel;
import com.rafa.kafkaestudo.domain.person.ports.PersonServicePort;
import com.rafa.kafkaestudo.domain.person.ports.PersonRepositoryPort;
import com.rafa.kafkaestudo.domain.ports.QueueProducerPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonServiceImplPort implements PersonServicePort {

    private PersonRepositoryPort personRepository;
    private QueueProducerPort queueProducer;

    public PersonServiceImplPort(PersonRepositoryPort personRepositoryPort, QueueProducerPort queueProducer) {
        this.personRepository = personRepositoryPort;
        this.queueProducer = queueProducer;
    }

    @Override
    public Mono<List<PersonModel>> listAll() {
        List<PersonModel> listPersonModel = new ArrayList<>();
        return personRepository
                .listAll()
                .map(listPersonModel::add)
                .then(Mono.just(listPersonModel));
    }

    @Override
    public Mono<PersonModel> getById(String id) {
         return personRepository.getById(id)
                 .switchIfEmpty(Mono.error(new PersonNotFoundException("Person not found in database")));
    }

    @Override
    public Mono<String> createPersonToQueue(PersonModel personModel) {
        try {
            String payload = new ObjectMapper().writeValueAsString(personModel);
            queueProducer.sendMessage("cadastro_pessoas", payload);
            return Mono.just(personModel.id());
        }catch (JsonProcessingException e) {
           return Mono.error(new Exception(e.getMessage()));
        }
    }

    @Override
    public Mono<String> createPerson(PersonModel personModel) {
        return this.personRepository.create(personModel);
    }
}
