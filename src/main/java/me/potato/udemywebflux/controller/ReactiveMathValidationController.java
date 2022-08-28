package me.potato.udemywebflux.controller;

import lombok.RequiredArgsConstructor;
import me.potato.udemywebflux.dto.Response;
import me.potato.udemywebflux.exception.InputValidationException;
import me.potato.udemywebflux.service.ReactiveMathService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reactive-math")
public class ReactiveMathValidationController {
    private final ReactiveMathService mathService;

    @GetMapping("/square/{input}/throw")
    public Mono<Response> findSquareOrThrow(@PathVariable Integer input) {
        if (input < 10 || input > 20)
            throw new InputValidationException(input);

        return mathService.findSquare(input);
    }

    @GetMapping("/square/{input}/mono-throw")
    public Mono<Response> findSquareOrMonoThrow(@PathVariable Mono<Integer> input) {
        return input
                .handle(((integer, sink) -> {
                    if (integer < 10 || integer > 20)
                        sink.error(new InputValidationException(integer));
                    else
                        sink.next(integer);
                }))
                .cast(Integer.class)
                .flatMap(mathService::findSquare);
    }


    @GetMapping("/square/{input}/assignment")
    public Mono<ResponseEntity<Response>> assignmentOrMonoThrow(@PathVariable Integer input) {
        return Mono.just(input)
                .filter(integer -> !(integer < 10 || integer > 20))
                .flatMap(mathService::findSquare)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}
