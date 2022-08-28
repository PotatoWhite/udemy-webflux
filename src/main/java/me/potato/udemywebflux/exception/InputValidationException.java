package me.potato.udemywebflux.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class InputValidationException extends RuntimeException {

    private static final String MSG = "allowed range is 10 to 20";

    private static final int errorCode = 100;
    private final        int input;

    public InputValidationException(int input) {
        super(MSG);
        this.input = input;
    }

    public String getMessage(){
        return InputValidationException.MSG;
    }

    public int getErrorCode(){
        return InputValidationException.errorCode;
    }


}
