package dodam.b1nd.dgit.global.error;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        HttpStatus status, String msg
) {}