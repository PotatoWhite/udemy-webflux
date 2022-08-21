package me.potato.udemywebflux.controller;

import lombok.RequiredArgsConstructor;
import me.potato.udemywebflux.dto.Response;
import me.potato.udemywebflux.service.MathService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/math")
public class MathController {
    private final MathService mathService;

    @GetMapping("/square/{input}")
    private Response findSquare( @PathVariable Integer input) {
        return mathService.findSquare(input);
    }

    @GetMapping("/table/{input}")
    private List<Response> multiplicationTable(@PathVariable Integer input) {
        return mathService.multiplicationTable(input);
    }
}
