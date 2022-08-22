package me.potato.udemywebflux;


import me.potato.udemywebflux.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lec02GetSingleResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest() {
        var response = this.webClient.get()
                .uri("/reactive-math/square/{number}", 10)
                .retrieve()
                .bodyToMono(Response.class)
                .block();

        System.out.println(response);
    }


    @Test
    public void stepVerifierTest() {
        var response = this.webClient.get()
                .uri("/reactive-math/square/{number}", 10)
                .retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.getOutput() == 100)
                .verifyComplete();
    }
}


