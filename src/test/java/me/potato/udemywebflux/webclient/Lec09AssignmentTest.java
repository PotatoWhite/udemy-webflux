package me.potato.udemywebflux.webclient;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

public class Lec09AssignmentTest extends BaseTest {
    private final static String FORMAT = "%d %s %d = %s";
    private final static int    A      = 10;

    @Autowired
    private WebClient webClient;

    @Test
    public void test() {
        var response = Flux.range(1, 5)
                .flatMap(b -> Flux.just("+", "-", "*", "/").flatMap(op -> send(b, op)))
                .doOnNext(System.out::println);

        StepVerifier.create(response)
                .expectNextCount(20)
                .thenConsumeWhile(Objects::nonNull)
                .verifyComplete();
    }

    @NotNull
    private Mono<String> send(int b, String op) {
        return this.webClient.get()
                .uri("/calculator/{a}/{b}", A, b)
                .headers(httpHeaders -> httpHeaders.set("OP", op))
                .retrieve()
                .bodyToMono(Integer.class)
                .map(v -> String.format(FORMAT, A, op, b, v));

    }

}
