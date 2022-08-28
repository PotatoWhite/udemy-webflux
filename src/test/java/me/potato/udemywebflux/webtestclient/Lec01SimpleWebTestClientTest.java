package me.potato.udemywebflux.webtestclient;

import me.potato.udemywebflux.dto.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;


// integration test
@SpringBootTest
@AutoConfigureWebTestClient
public class Lec01SimpleWebTestClientTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void stepVerifierTest() {
        var response = this.client.get()
                .uri("/reactive-math/square/{number}", 10)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Response.class)
                .getResponseBody();

        StepVerifier.create(response)
                .expectNextMatches(r -> r.getOutput() == 100)
                .verifyComplete();
    }

    @Test
    public void fluentVerifierTest() {
        this.client.get()
                .uri("/reactive-math/square/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(25));

    }
}
