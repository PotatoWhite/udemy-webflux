package me.potato.udemywebflux.webtestclient;

import me.potato.udemywebflux.controller.ReactiveMathController;
import me.potato.udemywebflux.dto.MultiplyRequestDto;
import me.potato.udemywebflux.dto.Response;
import me.potato.udemywebflux.service.ReactiveMathService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = { ReactiveMathController.class})
public class Lec03ControllerPostTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private ReactiveMathService service;

    // test /reactive-math/multiply with postmapping
    @Test
    public void multiplyTest() {
        Mockito.when(service.multiply(Mockito.any())).thenReturn(Mono.just(new Response(10)));

        client.post()
                .uri("/reactive-math/multiply")
                .accept(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBasicAuth("username", "password"))
                .headers(h -> h.set("somekey", "somevalue"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new MultiplyRequestDto(10, 10))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(10));
    }



}
