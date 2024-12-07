package dodam.b1nd.dgit.domain.auth.presentation.controller;

import dodam.b1nd.dgit.domain.auth.presentation.dto.request.LoginDto;
import dodam.b1nd.dgit.domain.auth.presentation.dto.request.ReProviderDto;
import dodam.b1nd.dgit.domain.auth.presentation.dto.response.ReProvideToken;
import dodam.b1nd.dgit.domain.auth.presentation.dto.response.TokenDto;
import dodam.b1nd.dgit.domain.auth.service.AuthService;
import dodam.b1nd.dgit.domain.user.domain.entity.User;
import dodam.b1nd.dgit.global.lib.jwt.JwtUtil;
import dodam.b1nd.dgit.global.response.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Auth", description = "Auth Api")
@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "login", description = "DAuth 로그인")
    @PostMapping(value = "/login")
    public ResponseData<TokenDto> login(@RequestBody @Valid LoginDto loginDto) {
        TokenDto tokenDto = authService.getToken(loginDto);
        return ResponseData.of(HttpStatus.OK, "login 성공", tokenDto);
    }

    @Operation(summary = "refresh", description = "토큰 Refresh")
    @PostMapping(value = "/refresh")
    public ResponseData<ReProvideToken> reProvider(@RequestBody @Valid ReProviderDto reProviderDto) {
        User user = jwtUtil.getUserByToken(reProviderDto.getRefreshToken());
        ReProvideToken reProvideToken = authService.reProvideToken(user.getEmail(), user.getRole());
        return ResponseData.of(HttpStatus.OK, "refresh 성공", reProvideToken);
    }
}
