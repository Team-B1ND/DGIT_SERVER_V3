package dodam.b1nd.dgit.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저"),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않음"),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 Admin"),
    WEEK_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 Week"),
    GITHUB_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 Github 유저"),
    GITHUB_REPOSITORY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 Github 레포지토리"),
    GITHUB_REPOSITORY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 Github 레포지토리"),
    GITHUB_USER_EXIST(HttpStatus.CONFLICT, "이미 존재하는 Github 유저"),
    INVALID_PERMISSION(HttpStatus.BAD_REQUEST, "유효하지 않은 권한"),
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "유효하지 않은 토큰"),
    TOKEN_NOT_PROVIDED(HttpStatus.BAD_REQUEST, "토큰이 입력되지 않았습니다"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰"),
    WEB_CLIENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류"),
    INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류");

    private final HttpStatus status;
    private final String message;
}
