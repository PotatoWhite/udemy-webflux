package me.potato.udemywebflux.config;

import lombok.RequiredArgsConstructor;
import me.potato.udemywebflux.dto.InputFailedValidationResponse;
import me.potato.udemywebflux.exception.InputValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
@Configuration
public class RouterConfig {
    private final RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> routerHighLevelRouter() {
        return RouterFunctions.route()
                .path("router", this::serverResponseRouterFunction)
                .build();
    }

    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("/square/{input}", RequestPredicates.path("*/1?").or(RequestPredicates.path("*/20")), requestHandler::squareHandler)
                .GET("/square/{input}", req -> ServerResponse.badRequest().bodyValue("only 10-19 allowed"))
                .GET("/table/{input}", requestHandler::tableHandler)
                .GET("/table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("/multiply", requestHandler::multiplyHandler)
                .GET("/square/{input}/validation", requestHandler::squareHandlerWithValidation)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (error, request) -> {
            var ex = (InputValidationException) error;
            var response = new InputFailedValidationResponse();
            response.setInput(ex.getInput());
            response.setMessage(ex.getMessage());
            response.setErrorCode(ex.getErrorCode());

            return ServerResponse.badRequest().bodyValue(response);
        };
    }


}
