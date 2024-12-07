package dodam.b1nd.dgit.domain.auth.service;

import dodam.b1nd.dgit.domain.auth.presentation.dto.request.LoginDto;
import dodam.b1nd.dgit.domain.auth.presentation.dto.request.ReProviderDto;
import dodam.b1nd.dgit.domain.auth.presentation.dto.response.ReProvideToken;
import dodam.b1nd.dgit.domain.auth.presentation.dto.response.TokenDto;
import dodam.b1nd.dgit.domain.auth.presentation.dto.api.UserInfoDto;
import dodam.b1nd.dgit.domain.token.service.TokenService;
import dodam.b1nd.dgit.domain.user.domain.entity.User;
import dodam.b1nd.dgit.domain.user.domain.enums.Role;
import dodam.b1nd.dgit.domain.user.service.UserService;
import dodam.b1nd.dgit.global.lib.webclient.template.DodamWebClientTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenService tokenService;
    private final UserService userService;
    private final DodamWebClientTemplate webClientTemplate;

    public TokenDto getToken(LoginDto loginDto) {
        return getUserInfo(webClientTemplate.auth(loginDto.getCode(),"/token"));
    }

    public TokenDto getUserInfo(String accessToken) {
        UserInfoDto infoDto = webClientTemplate.openApi(accessToken, "/user").getData();

        User user = userService.save(User.builder()
                .email(infoDto.getEmail())
                .name(infoDto.getName())
                .role(Role.STUDENT)
                .build());

        return createUserToken(user.getEmail(), user.getRole());
    }

    private TokenDto createUserToken(String email, @NotNull Role role) {
        return TokenDto.builder()
                .accessToken(tokenService.generateAccessToken(email, role))
                .refreshToken(tokenService.generateRefreshToken(email, role))
                .build();
    }

    public ReProvideToken reProvideToken(String email, @NotNull Role role){
        return ReProvideToken.builder()
                .accessToken(tokenService.generateAccessToken(email, role))
                .build();
    }
}
