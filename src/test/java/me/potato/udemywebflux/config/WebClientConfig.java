package me.potato.udemywebflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
//        ExchangeFilterFunction
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .filter(this::sessionToken)
                .build();
    }

    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex) {
        // auth --> basic or bearer
        var _request = request.attribute("auth")
                        .map(v -> v.equals("basic") ? withBasicAuth(request) : withOAuth(request))
                        .orElse(request);
        return ex.exchange(_request);
    }

    private ClientRequest withBasicAuth(ClientRequest request) {
        return ClientRequest.from(request)
                .headers(h -> h.setBasicAuth("username", "password")).build();
    }

    private ClientRequest withOAuth(ClientRequest request) {
        return ClientRequest.from(request)
                .headers(h -> h.setBearerAuth("some-token")).build();
    }
}
