package me.potato.udemywebflux;

import me.potato.udemywebflux.dto.MultiplyRequestDto;
import me.potato.udemywebflux.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lec04HeadersTest extends BaseTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void headersTest() {
        var response = this.webClient.post()
                .uri("/reactive-math/multiply")
                .headers(h -> h.set("somekey", "somevalue"))
                .bodyValue(buildRequestDto(5, 3))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);


        StepVerifier.create(response)
                .expectNextMatches(r -> r.getOutput() == 15)
                .verifyComplete();
    }


    @Test
    public void basicAuthTest() {
        var response = this.webClient.post()
                .uri("/reactive-math/multiply")
                .bodyValue(buildRequestDto(5, 3))
                .headers(h -> h.set("somekey", "somevalue"))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);


        StepVerifier.create(response)
                .expectNextMatches(r -> r.getOutput() == 15)
                .verifyComplete();
    }


    private MultiplyRequestDto buildRequestDto(int a, int b) {
        return new MultiplyRequestDto(a, b);
    }
}
