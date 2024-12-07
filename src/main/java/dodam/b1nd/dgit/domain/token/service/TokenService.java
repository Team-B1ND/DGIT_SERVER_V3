package dodam.b1nd.dgit.domain.token.service;

import dodam.b1nd.dgit.domain.user.domain.enums.Role;
import dodam.b1nd.dgit.global.lib.jwt.JwtType;
import dodam.b1nd.dgit.global.lib.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;

    private static final Long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 3600 * 24 * 3; // 24시간
    private static final Long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 3600 * 24 * 3 * 60; // 3일
//    private static final Long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 3; // 3분

    public String generateAccessToken(String email, @NotNull Role role) {
        return jwtUtil.generateToken(email, ACCESS_TOKEN_EXPIRE_TIME, JwtType.ACCESS, role);
    };

    public String generateRefreshToken(String email, @NotNull Role role) {
        return jwtUtil.generateToken(email, REFRESH_TOKEN_EXPIRE_TIME, JwtType.REFRESH, role);
    };
}
