package com.rafa.kafkaestudo.application.mappers;

import com.rafa.kafkaestudo.application.requests.PersonRequest;
import com.rafa.kafkaestudo.domain.person.model.PersonModel;

import java.util.UUID;

public class PersonRequestMapper {
    public static PersonModel toPersonModel(PersonRequest personRequest) {
         return new PersonModel(UUID.randomUUID().toString(), personRequest.name(), personRequest.age());
    }
}
