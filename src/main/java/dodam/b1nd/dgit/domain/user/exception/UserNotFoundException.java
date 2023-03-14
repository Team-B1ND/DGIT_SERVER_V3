package dodam.b1nd.dgit.domain.user.exception;

import dodam.b1nd.dgit.global.error.CustomException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException {
    public static final CustomException EXCEPTION = new UserNotFoundException();

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "user not found");
    }

}