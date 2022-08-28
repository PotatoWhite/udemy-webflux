package me.potato.udemywebflux.webtestclient;

import me.potato.udemywebflux.config.RequestHandler;
import me.potato.udemywebflux.config.RouterConfig;
import me.potato.udemywebflux.dto.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {RouterConfig.class})
public class Lec05RouterFunctionTest {
    private WebTestClient client;

//    @Autowired
//    private RouterConfig routerConfig;

    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    private RequestHandler requestHandler;

    @BeforeAll
    public void setup() {
//        client = WebTestClient.bindToRouterFunction(routerConfig.routerHighLevelRouter()).build();
        client = WebTestClient.bindToApplicationContext(applicationContext).build();

//        to integration test
//        WebTestClient.bindToServer().baseUrl("http://localhost:8080").build()
    }

    @Test
    public void test() {
        Mockito.when(requestHandler.squareHandler(Mockito.any()))
                .thenReturn(ServerResponse.ok().bodyValue(new Response(1)));

        client.get()
                .uri("/router/square/{number}", 15)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Response.class)
                .value(response -> Assertions.assertThat(response.getOutput()).isEqualTo(1));
    }

}
