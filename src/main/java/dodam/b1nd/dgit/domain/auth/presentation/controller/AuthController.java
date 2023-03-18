package dodam.b1nd.dgit.domain.auth.presentation.controller;

import dodam.b1nd.dgit.domain.auth.presentation.dto.LoginDto;
import dodam.b1nd.dgit.domain.auth.presentation.dto.TokenDto;
import dodam.b1nd.dgit.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Auth", description = "Auth Api")
@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "login", description = "DAuth 로그인")
    @PostMapping(value = "/login")
    public TokenDto login(@RequestBody @Valid LoginDto loginDto) {
        TokenDto tokenDto = authService.getToken(loginDto);
        return tokenDto;
    }

}
