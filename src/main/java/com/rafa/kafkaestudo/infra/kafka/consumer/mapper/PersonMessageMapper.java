package com.rafa.kafkaestudo.infra.kafka.consumer.mapper;

import com.rafa.kafkaestudo.domain.person.model.PersonModel;
import com.rafa.kafkaestudo.infra.kafka.consumer.model.PersonMessageModel;

public class PersonMessageMapper {
    public static PersonModel toPersonModel(PersonMessageModel personMessageModel) {
        return new PersonModel(personMessageModel.id(), personMessageModel.name(), personMessageModel.age());
    }
}
