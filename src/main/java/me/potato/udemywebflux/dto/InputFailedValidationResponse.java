package me.potato.udemywebflux.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InputFailedValidationResponse {
    private int errorCode;
    private int input;
    private String message;
}
