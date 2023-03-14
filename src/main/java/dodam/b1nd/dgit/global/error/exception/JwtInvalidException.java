package dodam.b1nd.dgit.global.error.exception;

import dodam.b1nd.dgit.global.error.CustomException;
import org.springframework.http.HttpStatus;

public class JwtInvalidException extends CustomException {
    public static final CustomException EXCEPTION = new JwtInvalidException();

    public JwtInvalidException() {
        super(HttpStatus.UNAUTHORIZED, "invalid token");
    }

}