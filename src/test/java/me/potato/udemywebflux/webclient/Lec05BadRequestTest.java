package me.potato.udemywebflux.webclient;

import me.potato.udemywebflux.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

public class Lec05BadRequestTest extends BaseTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void badRequestTest() {
        var response = this.webClient.get()
                .uri("/reactive-math/square/{number}/throw", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println)
                .doOnError(System.out::println);

        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyError(WebClientResponseException.BadRequest.class);
    }
}
