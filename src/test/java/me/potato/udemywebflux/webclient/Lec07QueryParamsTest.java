package me.potato.udemywebflux.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.test.StepVerifier;

import java.util.Map;

public class Lec07QueryParamsTest extends BaseTest {
    @Autowired
    private WebClient webClient;

    String queryString = "http://localhost:8080/jobs/search?count={count}&page={page}";

    @Test
    public void queryParamsTest1() {
        UriComponentsBuilder.fromUriString(queryString)
                .build();

        var response = this.webClient
                .get()
                .uri(queryString, 10, 20)
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(response)
                .expectNextMatches(r -> r == 10)
                .expectNextMatches(r -> r == 20)
                .verifyComplete();

    }

    @Test
    public void queryParamsTest2() {
        var response = this.webClient
                .get()
                .uri(b -> b.path("jobs/search").query("count={count}&page={page}").build(10, 20))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(response)
                .expectNextMatches(r -> r == 10)
                .expectNextMatches(r -> r == 20)
                .verifyComplete();

    }

    @Test
    public void queryParamsTest3() {
        var response = this.webClient
                .get()
                .uri(b -> b.path("jobs/search")
                        .queryParam("count", 10)
                        .queryParam("page", 20)
                        .build(10, 20))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(response)
                .expectNextMatches(r -> r == 10)
                .expectNextMatches(r -> r == 20)
                .verifyComplete();

    }

    @Test
    public void queryParamsTest4() {

        var map = Map.of(
                "count", 10,
                "page", 20
        );
        var response = this.webClient
                .get()
                .uri(b -> b.path("jobs/search")
                        .query("count={count}&page={page}")
                        .build(map))

                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(response)
                .expectNextMatches(r -> r == 10)
                .expectNextMatches(r -> r == 20)
                .verifyComplete();

    }

    @Test
    public void getGroupByCKey() {
        webClient.get()
                .uri(URI -> URI.path("/getuser.api?userid={id}").build("nv_drive021"))
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(System.out::println);

    }

}


