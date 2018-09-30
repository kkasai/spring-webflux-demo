package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PersonHandler {
    private final PersonRepository personRepository;

    public PersonHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().body(BodyInserters.fromObject("Hello World"));
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(personRepository.findAll(), Person.class);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<Person> personMono = request.bodyToMono(Person.class);
        return personMono
                .flatMap(personRepository::save)
                .flatMap(person ->
                        ServerResponse
                                .created(request.uriBuilder().build())
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromObject(person)));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        return personRepository
                .findById(request.pathVariable("id"))
                .flatMap(person -> ServerResponse.noContent().build(personRepository.delete(person)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
