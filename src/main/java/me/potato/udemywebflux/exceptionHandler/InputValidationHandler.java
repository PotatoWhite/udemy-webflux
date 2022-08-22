package me.potato.udemywebflux.exceptionHandler;

import me.potato.udemywebflux.dto.InputFailedValidationResponse;
import me.potato.udemywebflux.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages="me.potato.udemywebflux.controller")
public class InputValidationHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException e) {
        var response = new InputFailedValidationResponse();
        response.setErrorCode(InputValidationException.getErrorCode());
        response.setMessage(InputValidationException.getMSG());
        response.setInput(e.getInput());

        return ResponseEntity.badRequest().body(response);
    }


}
