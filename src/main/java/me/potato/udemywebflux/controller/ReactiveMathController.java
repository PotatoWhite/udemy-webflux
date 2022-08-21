package me.potato.udemywebflux.controller;

import lombok.RequiredArgsConstructor;
import me.potato.udemywebflux.dto.Response;
import me.potato.udemywebflux.service.ReactiveMathService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reactive-math")
public class ReactiveMathController {
    private final ReactiveMathService mathService;

    @GetMapping("/square/{input}")
    private Mono<Response> findSquare(@PathVariable Integer input) {
        return mathService.findSquare(input);
    }

    @GetMapping("/table/{input}")
    private Flux<Response> multiplicationTable(@PathVariable Integer input) {
        return mathService.multiplicationTable(input);
    }

    @GetMapping(path = "/table/{input}/stream", produces = "text/event-stream")
    private Flux<Response> multiplicationTableStream(@PathVariable Integer input) {
        return mathService.multiplicationTable(input);
    }
}
