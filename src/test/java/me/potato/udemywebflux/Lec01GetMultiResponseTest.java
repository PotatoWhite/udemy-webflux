package me.potato.udemywebflux;


import me.potato.udemywebflux.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lec01GetMultiResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void fluxTest(){
        var response = this.webClient.get()
                .uri("/reactive-math/table/{number}", 10)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(response)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void fluxStreamTest(){
        var response = this.webClient.get()
                .uri("/reactive-math/table/{number}/stream", 10)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(response)
                .expectNextCount(10)
                .verifyComplete();
    }

}


