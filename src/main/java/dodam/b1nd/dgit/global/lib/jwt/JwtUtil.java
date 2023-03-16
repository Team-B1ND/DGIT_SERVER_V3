package dodam.b1nd.dgit.global.lib.jwt;

import dodam.b1nd.dgit.domain.user.domain.entity.User;
import dodam.b1nd.dgit.domain.user.service.UserService;
import dodam.b1nd.dgit.global.error.CustomError;
import dodam.b1nd.dgit.global.error.ErrorCode;
import dodam.b1nd.dgit.global.properties.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.events.EventException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;
    private final UserService userService;

    private Key getSignKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, Long time, JwtType type) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("type", type);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + time))
                .signWith(getSignKey(jwtProperties.getKey()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException, IllegalArgumentException, UnsupportedJwtException, MalformedJwtException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey(jwtProperties.getKey()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw CustomError.of(ErrorCode.TOKEN_EXPIRED);
        } catch (IllegalArgumentException e) {
            throw CustomError.of(ErrorCode.TOKEN_NOT_PROVIDED);
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            throw CustomError.of(ErrorCode.INVALID_TOKEN);
        } catch (EventException e) {
            throw e;
        }
    }

    public JwtType checkTokenType(String token) {
        if ("REFRESH".equals(extractAllClaims(token).get("type"))) {
            return JwtType.REFRESH;
        } else {
            return JwtType.ACCESS;
        }
    }

    public User getUserByToken(String token) {
        return userService.getUserByEmail(extractAllClaims(token).get("email").toString());
    }
}