package dodam.b1nd.dgit.global.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class Response {

    private int status;
    private String message;

    public static org.springframework.http.ResponseEntity<Response> responseEntity(HttpStatus status, String message){
        return org.springframework.http.ResponseEntity
                .status(status)
                .body(Response.builder()
                        .status(status.value())
                        .message(message)
                        .build());
    }

}