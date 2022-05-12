package com.rafa.kafkaestudo.infra.kafka.consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PersonMessageModel(
      @JsonProperty(value = "id")
      String id,
      @JsonProperty(value = "name")
      String name,
      @JsonProperty(value="age")
      Integer age
)
{}
