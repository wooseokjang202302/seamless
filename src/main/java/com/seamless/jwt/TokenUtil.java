package com.seamless.jwt;

import com.seamless.dto.UserRequestDto;
import com.seamless.entity.UserEntity;
import com.seamless.exception.UnauthorizedException;
import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${token.access-token-duration}")
    private long accessTokenDuration;

    @Value("${token.refresh-token-duration}")
    private long refreshTokenDuration;


    public TokenResponse generateToken(UserEntity userEntity) {
        String accessToken = createToken(userEntity, accessTokenDuration);  // 15분
        String refreshToken = createToken(userEntity, refreshTokenDuration);  // 7일

        return new TokenResponse(accessToken, refreshToken);
    }

    public AccessTokenResponse generateAccessToken(UserEntity userEntity) {
        String accessToken = createToken(userEntity, accessTokenDuration); // 15분

        return new AccessTokenResponse(accessToken);
    }

    private String createToken(UserEntity userEntity, long duration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + duration);

        return Jwts.builder()
                .setSubject(Long.toString(userEntity.getId()))
                .claim("email", userEntity.getEmail())
                .setIssuedAt(now)  // 발행일
                .setExpiration(expiryDate)  // 만료일
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            return !claims.getExpiration().before(new Date());

        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("액세스 토큰이 만료되어 있습니다.");
        } catch (JwtException | IllegalArgumentException e) {
            throw new UnauthorizedException("액세스 토큰의 형식이 올바르지 않습니다.");
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            return !claims.getExpiration().before(new Date());

        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("리프레시 토큰이 만료되어 있습니다.");
        } catch (JwtException | IllegalArgumentException e) {
            throw new UnauthorizedException("리프레시 토큰의 형식이 올바르지 않습니다.");
        }
    }

    public String getUserIdFromRefreshToken(String refreshToken) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(refreshToken)
                .getBody()
                .getSubject();
    }

    public static class TokenResponse {
        private String accessToken;
        private String refreshToken;

        public TokenResponse(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }

    public static class AccessTokenResponse {
        private String accessToken;

        public AccessTokenResponse(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }
}