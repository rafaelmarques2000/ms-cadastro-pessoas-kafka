package com.rafa.kafkaestudo.domain.person.ports;

import com.rafa.kafkaestudo.domain.person.model.PersonModel;
import reactor.core.publisher.Mono;
import java.util.List;

public interface PersonServicePort {
     Mono<List<PersonModel>> listAll();
     Mono<PersonModel> getById(String id);
     Mono<String> createPersonToQueue(PersonModel personModel);

     Mono<String> createPerson(PersonModel personModel);
}
