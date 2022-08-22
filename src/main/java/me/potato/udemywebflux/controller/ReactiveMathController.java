package me.potato.udemywebflux.controller;

import lombok.RequiredArgsConstructor;
import me.potato.udemywebflux.dto.MultiplyRequestDto;
import me.potato.udemywebflux.dto.Response;
import me.potato.udemywebflux.service.ReactiveMathService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

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
//        AbstractJackson2Encoder
//        Mono<List<Response>>
        return mathService.multiplicationTable(input);
    }

    @GetMapping(path="/table/{input}/stream", produces="text/event-stream")
    private Flux<Response> multiplicationTableStream(@PathVariable Integer input) {
        return mathService.multiplicationTableStream(input);
    }

    @GetMapping(path="/table/{input}/notstream", produces="text/event-stream")
    private Flux<Response> multiplicationTableNotStream(@PathVariable Integer input) {
        return mathService.multiplicationTable(input);
    }

    @PostMapping("/multiply")
    private Mono<Response> multiply(
            @RequestBody Mono<MultiplyRequestDto> input,
            @RequestHeader Map<String, String> headers) {
        System.out.println(headers);
        return mathService.multiply(input);
    }
}
