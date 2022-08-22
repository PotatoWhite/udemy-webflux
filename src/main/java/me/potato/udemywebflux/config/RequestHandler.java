package me.potato.udemywebflux.config;

import lombok.RequiredArgsConstructor;
import me.potato.udemywebflux.dto.InputFailedValidationResponse;
import me.potato.udemywebflux.dto.MultiplyRequestDto;
import me.potato.udemywebflux.dto.Response;
import me.potato.udemywebflux.service.ReactiveMathService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class RequestHandler {
    private final ReactiveMathService mathService;

    public Mono<ServerResponse> squareHandler(ServerRequest request) {
        var input  = request.pathVariable("input");
        var result = mathService.findSquare(Integer.parseInt(input));
        return ServerResponse.ok().body(result, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest request) {
        var input  = request.pathVariable("input");
        var result = mathService.multiplicationTableStream(Integer.parseInt(input));
        return ServerResponse.ok().body(result, Response.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest request) {
        var input  = request.pathVariable("input");
        var result = mathService.multiplicationTableStream(Integer.parseInt(input));
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(result, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest request) {
        var body   = request.bodyToMono(MultiplyRequestDto.class);
        var result = mathService.multiply(body);
        return ServerResponse.ok()
                .body(result, Response.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest request) {
        var input = Integer.parseInt(request.pathVariable("input"));
        if (input < 10 || input > 20){
            var response = new InputFailedValidationResponse();
            return ServerResponse.badRequest().build();
        }

        var result = mathService.findSquare(input);
        return ServerResponse.ok().body(result, Response.class);
    }
}
