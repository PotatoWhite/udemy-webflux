package me.potato.udemywebflux.webtestclient;

import me.potato.udemywebflux.controller.ReactiveMathValidationController;
import me.potato.udemywebflux.dto.Response;
import me.potato.udemywebflux.exception.InputValidationException;
import me.potato.udemywebflux.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


@WebFluxTest(controllers = {ReactiveMathValidationController.class})
public class Lec04ErrorHandlingTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private ReactiveMathService service;

    // test error handling
    @Test
    public void errorHandlingTest() {
        Mockito.when(service.findSquare(Mockito.anyInt())).thenReturn(Mono.just(new Response(1)));

        client.get()
                .uri("/reactive-math/square/{number}/throw", 9)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("allowed range is 10 to 20")
                .jsonPath("$.input").isEqualTo(9)
                .jsonPath("$.errorCode").isEqualTo(100);

    }
}
