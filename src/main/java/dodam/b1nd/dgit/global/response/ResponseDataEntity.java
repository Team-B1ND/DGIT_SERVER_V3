package dodam.b1nd.dgit.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ResponseDataEntity<T> extends Response {

    private final T data;

    private ResponseDataEntity(HttpStatus status, String message, T data) {
        super(status.value(), message);
        this.data = data;
    }

    public static <T> ResponseDataEntity<T> of(HttpStatus status, String message, T data) {
        return new ResponseDataEntity<>(status, message, data);
    }

    public static <T> org.springframework.http.ResponseEntity<ResponseDataEntity<T>> responseEntity(HttpStatus status, String message, T data) {
        return ResponseEntity
                .status(status)
                .body(of(status, message, data));
    }

}