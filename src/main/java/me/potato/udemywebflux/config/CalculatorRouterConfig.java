package me.potato.udemywebflux.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@RequiredArgsConstructor
@Configuration
public class CalculatorRouterConfig {
    private final CalculatorHandler calculatorHandler;

    @Bean
    public RouterFunction<ServerResponse> calculatorHighLevelRouter1() {
        return RouterFunctions.route()
                .path("calculator", this::serverResponseRouterFunction)
                .build();
    }

    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("{a}/{b}",isOperation("+"), calculatorHandler::additionHandler)
                .GET("{a}/{b}",isOperation("-"), calculatorHandler::subtractionHandler)
                .GET("{a}/{b}",isOperation("*"), calculatorHandler::multiplicationHandler)
                .GET("{a}/{b}",isOperation("/"), calculatorHandler::divisionHandler)
                .GET("{a}/{b}", req -> ServerResponse.badRequest().bodyValue("OP should be + - * /"))
                .build();
    }

    private RequestPredicate isOperation(String operation) {
        return RequestPredicates.headers(headers -> operation.equals(headers.asHttpHeaders().toSingleValueMap().get("OP")));
    }



}
