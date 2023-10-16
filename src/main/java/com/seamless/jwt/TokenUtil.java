package com.seamless.jwt;

import com.seamless.dto.UserRequestDto;
import com.seamless.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY;


    public TokenResponse generateToken(UserEntity userEntity) {
        String accessToken = createToken(userEntity, 15 * 60 * 1000);  // 15분
        String refreshToken = createToken(userEntity, 7 * 24 * 60 * 60 * 1000);  // 7일

        return new TokenResponse(accessToken, refreshToken);
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

}