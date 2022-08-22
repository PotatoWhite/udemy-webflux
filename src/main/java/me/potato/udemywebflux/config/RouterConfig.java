package me.potato.udemywebflux.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@RequiredArgsConstructor
@Configuration
public class RouterConfig {
    private final RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("/router/square/{input}", requestHandler::squareHandler)
                .GET("/router/table/{input}", requestHandler::tableHandler)
                .GET("/router/table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("/router/multiply", requestHandler::multiplyHandler)
                .build();
    }

}
