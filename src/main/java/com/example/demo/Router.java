package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class Router {
    @Bean
    public RouterFunction<ServerResponse> routes(CustomerHandler demoHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/"), demoHandler::echo)
                .andRoute(RequestPredicates.GET("/all"), demoHandler::findAll)
                .andRoute(RequestPredicates.PUT("/register"), demoHandler::register);
    }
}
