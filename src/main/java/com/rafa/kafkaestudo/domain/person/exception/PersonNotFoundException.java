package com.rafa.kafkaestudo.domain.person.exception;

import com.rafa.kafkaestudo.domain.exception.DomainException;

public class PersonNotFoundException extends DomainException {
    public PersonNotFoundException(String message) {
        super(message);
    }
}
