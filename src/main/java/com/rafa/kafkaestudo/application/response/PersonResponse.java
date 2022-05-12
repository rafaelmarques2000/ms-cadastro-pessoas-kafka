package com.rafa.kafkaestudo.application.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PersonResponse(
        @JsonProperty(value = "id")
        String id,
        @JsonProperty(value = "name")
        String name,
        @JsonProperty(value = "age")
        Integer age
){}
