package me.potato.udemywebflux.webclient;

import me.potato.udemywebflux.dto.InputFailedValidationResponse;
import me.potato.udemywebflux.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06ExchangeTest extends BaseTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void exchangeTest() {
        var response = this.webClient
                .get()
                .uri("/reactive-math/square/{number}/throw", 10)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(System.out::println);

        StepVerifier.create(response)
                .expectNextMatches(r -> ((Response) r).getOutput() == 100)
                .verifyComplete();
    }

    @Test
    public void badRequestTest() {
        var response = this.webClient
                .get()
                .uri("/reactive-math/square/{number}/throw", 5)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(System.out::println);

        StepVerifier.create(response)
                .expectNextMatches(r -> ((InputFailedValidationResponse) r).getErrorCode() == 100)
                .verifyComplete();
    }


    private Mono<Object> exchange(ClientResponse response) {
        if (response.rawStatusCode() == HttpStatus.BAD_REQUEST.value())
            return response.bodyToMono(InputFailedValidationResponse.class);

        return response.bodyToMono(Response.class);
    }
}
