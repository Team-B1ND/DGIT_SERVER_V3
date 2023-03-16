package dodam.b1nd.dgit.global.lib.webclient;

import dodam.b1nd.dgit.global.config.webclient.WebClientConfig;
import dodam.b1nd.dgit.global.error.CustomError;
import dodam.b1nd.dgit.global.error.ErrorCode;
import dodam.b1nd.dgit.global.lib.webclient.parser.HeaderParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

    private final WebClientConfig webClientConfig;

    public <T> ResponseEntity<T> get(String url, Class<T> responseDtoClass) {
        return webClientConfig.webClient().method(HttpMethod.GET)
                .uri(url)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    switch (clientResponse.statusCode()) {
                        case BAD_REQUEST:
                            return Mono.error(CustomError.of(ErrorCode.WEB_CLIENT_ERROR));
                        case UNAUTHORIZED:
                            return Mono.error(CustomError.of(ErrorCode.TOKEN_EXPIRED));
                        case GONE:
                            return Mono.error(CustomError.of(ErrorCode.INVALID_TOKEN));
                        default:
                            return Mono.error(CustomError.of(ErrorCode.INTERNAL_SERVER));
                    }
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(CustomError.of(ErrorCode.INTERNAL_SERVER)))
                .toEntity(responseDtoClass)
                .block();
    }

    public <T> ResponseEntity<T> get(String url, HeaderParser headerParser, Class<T> responseDtoClass) {
        return webClientConfig.webClient().method(HttpMethod.GET)
                .uri(url)
                .header(headerParser.getType(), headerParser.getValue())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    switch (clientResponse.statusCode()) {
                        case BAD_REQUEST:
                            return Mono.error(CustomError.of(ErrorCode.WEB_CLIENT_ERROR));
                        case UNAUTHORIZED:
                            return Mono.error(CustomError.of(ErrorCode.TOKEN_EXPIRED));
                        case GONE:
                            return Mono.error(CustomError.of(ErrorCode.INVALID_TOKEN));
                        default:
                            return Mono.error(CustomError.of(ErrorCode.INTERNAL_SERVER));
                    }
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(CustomError.of(ErrorCode.INTERNAL_SERVER)))
                .toEntity(responseDtoClass)
                .block();
    }

    public <T, V> ResponseEntity<T> post(String url, V requestDto, Class<T> responseDtoClass) {
        return webClientConfig.webClient().method(HttpMethod.POST)
                .uri(url)
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    switch (clientResponse.statusCode()) {
                        case BAD_REQUEST:
                            return Mono.error(CustomError.of(ErrorCode.WEB_CLIENT_ERROR));
                        case UNAUTHORIZED:
                            return Mono.error(CustomError.of(ErrorCode.TOKEN_EXPIRED));
                        case GONE:
                            return Mono.error(CustomError.of(ErrorCode.INVALID_TOKEN));
                        default:
                            return Mono.error(CustomError.of(ErrorCode.INTERNAL_SERVER));
                    }
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(CustomError.of(ErrorCode.INTERNAL_SERVER)))
                .toEntity(responseDtoClass)
                .block();
    }
}
