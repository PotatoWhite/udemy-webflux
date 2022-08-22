package me.potato.udemywebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import reactor.netty.resources.LoopResources;

@SpringBootApplication
public class UdemyWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(UdemyWebfluxApplication.class, args);
    }

    @Bean
    public ReactiveWebServerFactory reactiveWebServerFactory() {
        var factory = new NettyReactiveWebServerFactory();
        final var reactorResourceFactory = new ReactorResourceFactory();
        reactorResourceFactory.setLoopResources(LoopResources.create("http", 1000, true));
        reactorResourceFactory.setUseGlobalResources(false);
        factory.setResourceFactory(reactorResourceFactory);
        return factory;
    }
}
