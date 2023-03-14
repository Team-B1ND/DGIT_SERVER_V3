package dodam.b1nd.dgit.global.lib.jwt;

import dodam.b1nd.dgit.domain.auth.presentation.dto.TokenResponse;
import dodam.b1nd.dgit.domain.user.domain.entity.User;
import dodam.b1nd.dgit.domain.user.service.UserService;
import dodam.b1nd.dgit.global.error.CustomException;
import dodam.b1nd.dgit.global.properties.JwtProperties;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;
    private final UserService userService;

    private String generateToken(JwtType jwtType, String id) {
        Date expiredDate = new Date();
        String secretKey;

        if(jwtType==JwtType.access) {
            secretKey = jwtProperties.getAccess();
            expiredDate = new Date(expiredDate.getTime() + jwtProperties.getAccess_expire());
        } else {
            secretKey = jwtProperties.getRefresh();
            expiredDate = new Date(expiredDate.getTime() + jwtProperties.getRefresh_expire());
        }

        return Jwts.builder()
                .setId(id)
                .setSubject(jwtType.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    private Claims getClaims(JwtType jwtType, final String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtType==JwtType.access ? jwtProperties.getAccess() : jwtProperties.getRefresh())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "expired token");
        }
    }

    public User getUserByToken(JwtType jwtType, final String token) {
        return userService.findById(getClaims(jwtType, token).getId());
    }

    public TokenResponse receiveToken(final String id) {
        return new TokenResponse(
                generateToken(JwtType.access, id),
                generateToken(JwtType.refresh, id)
        );
    }

    public String refreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "wrong token");
        }

        return generateToken(JwtType.access, getUserByToken(JwtType.refresh, refreshToken).getId());
    }

}