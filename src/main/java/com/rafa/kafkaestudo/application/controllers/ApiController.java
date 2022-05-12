package com.rafa.kafkaestudo.application.controllers;

import com.rafa.kafkaestudo.application.mappers.PersonRequestMapper;
import com.rafa.kafkaestudo.application.mappers.PersonResponseMapper;
import com.rafa.kafkaestudo.application.requests.PersonRequest;
import com.rafa.kafkaestudo.application.response.PersonResponse;
import com.rafa.kafkaestudo.application.response.SuccessResponse;
import com.rafa.kafkaestudo.domain.person.ports.PersonServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/persons")
public class ApiController {

    private PersonServicePort personServicePort;

    ApiController(PersonServicePort personServicePort) {
       this.personServicePort = personServicePort;
    }

    @GetMapping
    public Mono<ResponseEntity<List<PersonResponse>>> getPerson() {
        return personServicePort.listAll()
                .map(personModelsList -> ResponseEntity.ok().body(PersonResponseMapper.toPersonResponseList(personModelsList)))
                .doOnError(throwable -> System.out.println(throwable.getMessage()));
    }

    @GetMapping(path = "/{id}")
    public Mono<ResponseEntity<PersonResponse>> getPersonById(@PathVariable String id) {
        return personServicePort.getById(id)
                .map(personModel -> ResponseEntity.ok().body(PersonResponseMapper.toPersonResponse(personModel)))
                .doOnError(throwable -> System.out.println(throwable.getMessage()));
    }

    @PostMapping
    public Mono<ResponseEntity<SuccessResponse>> createPerson(@RequestBody PersonRequest personRequest) {
        return personServicePort.createPersonToQueue(PersonRequestMapper.toPersonModel(personRequest))
                .map(personId -> ResponseEntity.accepted().body(new SuccessResponse(personId, "Mensagem recebida com sucesso e será processado em breve")))
                .doOnError(throwable -> System.out.println(throwable.getMessage()));
    }
}
