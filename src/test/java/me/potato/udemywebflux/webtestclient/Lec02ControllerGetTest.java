package me.potato.udemywebflux.webtestclient;

import me.potato.udemywebflux.controller.ParamsController;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@WebFluxTest(controllers = {ParamsController.class, ReactiveMathController.class})
public class Lec02ControllerGetTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private ReactiveMathService service;

    @Test
    public void singleResponseTest() {
        Mockito.when(service.findSquare(Mockito.anyInt())).thenReturn(Mono.empty());

        client.get()
                .uri("/reactive-math/square/{number}", 10)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(r -> Assertions
                        .assertThat(r.getOutput()).isEqualTo(-1));
    }

    @Test
    public void listResponseTest() {

        var expectFlux = Flux.range(1, 3)
                .map(Response::new);


        Mockito.when(service.multiplicationTable(Mockito.anyInt())).thenReturn(expectFlux);

        client.get()
                .uri("/reactive-math/table/{number}", 10)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Response.class)
                .hasSize(3);
    }


    @Test
    public void listResponseTest_fail() {

        var expectFlux = Flux.range(1, 3)
                .map(Response::new);

        Mockito.when(service.multiplicationTable(Mockito.anyInt())).thenReturn(Flux.error(new IllegalArgumentException()));

        client.get()
                .uri("/reactive-math/table/{number}", -1)
                .exchange()
                .expectStatus().is5xxServerError();
    }


    @Test
    public void streamResponseTest() {

        var expectFlux = Flux.range(1, 3)
                .map(Response::new)
                .delayElements(Duration.ofMillis(100));


        Mockito.when(service.multiplicationTableStream(Mockito.anyInt())).thenReturn(expectFlux);

        client.get()
                .uri("/reactive-math/table/{number}/stream", 10)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .expectBodyList(Response.class)
                .hasSize(3);
    }

    @Test
    public void paramsTest() {

        var pageRequest = Map.of(
                "count", 10,
                "page", 20
        );

        client.get()
                .uri(b -> b.path("/jobs/search")
                        .query("count={count}&page={page}")
                        .build(pageRequest))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Integer.class)
                .hasSize(2).contains(10, 20);

    }

    // test /reactive-math/multiply with postmapping
    @Test
    public void multiplyTest() {
        Mockito.when(service.multiply(Mockito.any())).thenReturn(Mono.just(new Response(10)));

        client.post()
                .uri("/reactive-math/multiply")
                .contentType(MediaType.APPLICATION_JSON)
                .header("test", "test")
                .body(Mono.just(new MultiplyRequestDto(10, 1)), MultiplyRequestDto.class)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(10));
    }



}
