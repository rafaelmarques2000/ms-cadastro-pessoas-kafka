package com.rafa.kafkaestudo.application.mappers;

import com.rafa.kafkaestudo.application.response.PersonResponse;
import com.rafa.kafkaestudo.domain.person.model.PersonModel;

import java.util.ArrayList;
import java.util.List;

public class PersonResponseMapper {
     public static List<PersonResponse> toPersonResponseList(List<PersonModel> persons) {
         List<PersonResponse> personResp = new ArrayList<>();
         persons.forEach(personModel -> {
             personResp.add(new PersonResponse(personModel.id(), personModel.name(), personModel.age()));
         });
         return personResp;
     }

    public static PersonResponse toPersonResponse(PersonModel person) {
       return new PersonResponse(person.id(),person.name(),person.age());
    }
}
