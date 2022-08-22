package me.potato.udemywebflux.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class InputValidationException extends RuntimeException {

    @Getter
    private static final String MSG = "allow range is 10 - 20";

    @Getter
    private static final int errorCode = 100;
    private final        int input;

    public InputValidationException(int input) {
        super(MSG);
        this.input = input;
    }


}
