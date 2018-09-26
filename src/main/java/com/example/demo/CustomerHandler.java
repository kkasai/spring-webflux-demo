package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CustomerHandler {
    private final CustomerRepository customerRepository;

    public CustomerHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Mono<ServerResponse> echo(ServerRequest request) {
        return ServerResponse.ok().body(BodyInserters.fromObject("Hello World"));
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(customerRepository.findAll(), Customer.class);
    }

    public Mono<ServerResponse> register(ServerRequest request) {
        Mono<Customer> hello = request.bodyToMono(Customer.class);
        return hello
                .flatMap(customerRepository::save)
                .flatMap(helloEntity ->
                        ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromObject(helloEntity)));
    }
}
