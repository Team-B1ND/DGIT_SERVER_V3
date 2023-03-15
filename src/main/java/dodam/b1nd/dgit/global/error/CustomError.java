package dodam.b1nd.dgit.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class CustomError extends RuntimeException {
    private final ErrorCode errorCode;

    public static CustomError of(ErrorCode errorCode){
        return new CustomError(errorCode);
    }
}