package dodam.b1nd.dgit.domain.auth.presentation.controller;

import dodam.b1nd.dgit.domain.auth.presentation.dto.request.AdminLoginDto;
import dodam.b1nd.dgit.domain.auth.presentation.dto.request.LoginDto;
import dodam.b1nd.dgit.domain.auth.presentation.dto.response.TokenDto;
import dodam.b1nd.dgit.domain.auth.service.AdminAuthService;
import dodam.b1nd.dgit.domain.auth.service.AuthService;
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
    private final AdminAuthService adminAuthService;

    @Operation(summary = "login", description = "DAuth 로그인")
    @PostMapping(value = "/login")
    public ResponseData<TokenDto> login(@RequestBody @Valid LoginDto loginDto) {
        TokenDto tokenDto = authService.getToken(loginDto);
        return ResponseData.of(HttpStatus.OK, "login 성공", tokenDto);
    }

    @Operation(summary = "admin login", description = "Admin 로그인")
    @PostMapping(value = "/admin")
    public ResponseData<TokenDto> adminLogin(@RequestBody @Valid AdminLoginDto adminLoginDto) {
        TokenDto tokenDto = adminAuthService.getToken(adminLoginDto);
        return ResponseData.of(HttpStatus.OK, "admin login 성공", tokenDto);
    }
}
