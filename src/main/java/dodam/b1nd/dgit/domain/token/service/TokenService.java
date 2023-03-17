package dodam.b1nd.dgit.domain.token.service;

import dodam.b1nd.dgit.global.lib.jwt.JwtType;
import dodam.b1nd.dgit.global.lib.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;

    private static final Long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 3600 * 24; // 24시간
    private static final Long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 3600 * 24 * 3; // 3일

    public String generateAccessToken(String email) {
        return jwtUtil.generateToken(email, ACCESS_TOKEN_EXPIRE_TIME, JwtType.ACCESS);
    };

    public String generateRefreshToken(String email) {
        return jwtUtil.generateToken(email, REFRESH_TOKEN_EXPIRE_TIME, JwtType.REFRESH);
    };
}
