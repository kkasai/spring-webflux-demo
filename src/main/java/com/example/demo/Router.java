package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class Router {
    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(PersonHandler personHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/"), personHandler::hello)
                .andRoute(RequestPredicates.GET("/person"), personHandler::findAll)
                .andRoute(RequestPredicates.POST("/person"), personHandler::create)
                .andRoute(RequestPredicates.DELETE("/person/{id}"), personHandler::delete);
    }
}
