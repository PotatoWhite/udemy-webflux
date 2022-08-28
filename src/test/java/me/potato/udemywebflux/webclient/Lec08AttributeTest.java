package me.potato.udemywebflux.webclient;

import me.potato.udemywebflux.dto.MultiplyRequestDto;
import me.potato.udemywebflux.dto.Response;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lec08AttributeTest extends BaseTest{
    @Autowired
    private WebClient webClient;


    @Test
    public void basicTest() {
        var responseMono = this.webClient.post()
                .uri("/reactive-math/multiply")
                .bodyValue(buildRequestDto(5, 3))
                .attribute("auth", "basic")
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseMono)
                .expectNextMatches(r -> r.getOutput() == 15)
                .verifyComplete();

    }

    @Test
    public void bearerTest() {
        var responseMono = this.webClient.post()
                .uri("/reactive-math/multiply")
                .bodyValue(buildRequestDto(5, 3))
                .attribute("auth", "oauth")
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseMono)
                .expectNextMatches(r -> r.getOutput() == 15)
                .verifyComplete();

    }


    private @NotNull MultiplyRequestDto buildRequestDto(int a, int b) {
        return new MultiplyRequestDto(a, b);
    }


}
