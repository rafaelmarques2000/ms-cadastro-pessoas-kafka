package com.rafa.kafkaestudo.infra.database.mysql.queries;

public interface PersonQueries {
    String SELECT_ALL = "SELECT * FROM person";
    String SELECT_PERSON_BY_ID = "SELECT * FROM person WHERE id = :id";

    String CREATE_PERSON = """
            INSERT INTO person(id, name, age)VALUES(:id, :name, :age)
          """;
}
