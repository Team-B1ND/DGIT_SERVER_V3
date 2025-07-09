package dodam.b1nd.dgit.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public class CustomError extends RuntimeException {
    private final ErrorCode errorCode;

    private CustomError(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public static CustomError of(ErrorCode errorCode){
        return new CustomError(errorCode);
    }
}