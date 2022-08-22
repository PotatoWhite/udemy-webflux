package me.potato.udemywebflux.service;

import me.potato.udemywebflux.dto.MultiplyRequestDto;
import me.potato.udemywebflux.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ReactiveMathService {
    public Mono<Response> findSquare(int input) {
        return Mono.fromSupplier(() -> input * input)
                .map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input) {
//        return Flux.range(1, 10)
////                .doOnNext(i -> SleepUtil.sleepSeconds(1))
//                .delayElements(Duration.ofSeconds(1))
//                .doOnNext(i -> System.out.println("reactive-math-service processing: " + i + " " + Thread.currentThread().getName()))
//                .map(i -> input * i)
//                .map(Response::new);

        var result = IntStream.rangeClosed(1, 10)
                .peek(i -> SleepUtil.sleepSeconds(1))
                .peek(i -> System.out.println("math-service processing: " + i))
                .mapToObj(i -> new Response(i * input))
                .collect(Collectors.toList());

        return Flux.fromIterable(result);
    }

    public Flux<Response> multiplicationTableStream(int input) {
        return Flux.range(1, 10)
//                .doOnNext(i -> SleepUtil.sleepSeconds(1))
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("reactive-math-service processing: " + i + " " + Thread.currentThread().getName()))
                .map(i -> input * i)
                .map(Response::new);
    }


    public Mono<Response> multiply(Mono<MultiplyRequestDto> input) {
        return input.map(dto -> dto.getFirst() * dto.getSecond())
                .map(Response::new);
    }
}

