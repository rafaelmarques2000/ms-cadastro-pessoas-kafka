package com.rafa.kafkaestudo.infra.database.mysql.repository;

import com.rafa.kafkaestudo.domain.person.model.PersonModel;
import com.rafa.kafkaestudo.domain.person.ports.PersonRepositoryPort;
import com.rafa.kafkaestudo.infra.database.mysql.queries.PersonQueries;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Repository
public class PersonRepository implements PersonRepositoryPort {

    private DatabaseClient databaseClient;

    public PersonRepository(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Flux<PersonModel> listAll() {
        return databaseClient
                .sql(PersonQueries.SELECT_ALL)
                .map(parserPersonTableRow()).all();
    }

    @Override
    public Mono<PersonModel> getById(String id) {
        return databaseClient.sql(PersonQueries.SELECT_PERSON_BY_ID)
                .bind("id", id)
                .map(parserPersonTableRow())
                .one();
    }

    @Override
    public Mono<String> create(PersonModel personModel) {
        return databaseClient.sql(PersonQueries.CREATE_PERSON)
                .bind("id", personModel.id())
                .bind("name", personModel.name())
                .bind("age", personModel.age())
                .then().thenReturn(personModel.id());
    }

    private BiFunction<Row, RowMetadata, PersonModel> parserPersonTableRow() {
        return (row, rowMetadata) -> new PersonModel(
                row.get("id", String.class),
                row.get("name", String.class),
                row.get("age", Integer.class)
        );
    }
}
