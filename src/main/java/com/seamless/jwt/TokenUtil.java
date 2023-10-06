package com.seamless.jwt;

import com.seamless.domain.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    public TokenResponse generateToken(Users user) {
        String accessToken = createToken(user, 15 * 60 * 1000);  // 15분
        String refreshToken = createToken(user, 7 * 24 * 60 * 60 * 1000);  // 7일


        return new TokenResponse(accessToken, refreshToken);
    }

    private String createToken(Users user, long duration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + duration);

        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .claim("email", user.getEmail())
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

        @Override
        public String toString() {
            return "{" +
                    "\"access\" : \"" + accessToken + "\"," +
                    "\"refresh\": \"" + refreshToken + "\"" +
                    "}";
        }
    }

}