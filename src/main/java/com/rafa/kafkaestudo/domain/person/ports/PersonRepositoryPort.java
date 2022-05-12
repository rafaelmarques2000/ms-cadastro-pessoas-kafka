package com.rafa.kafkaestudo.domain.person.ports;

import com.rafa.kafkaestudo.domain.person.model.PersonModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepositoryPort {
    public Flux<PersonModel> listAll();
    public Mono<PersonModel> getById(String id);
    public Mono<String> create(PersonModel personModel);
}
