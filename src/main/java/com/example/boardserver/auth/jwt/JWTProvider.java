package com.example.boardserver.auth.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTProvider {
    
    private final SecretKey secretKey;
    
    public static final long TOKEN_VALID_TIME = 60 * 60 * 1000L;    // 수명 - 1시간

    public JWTProvider(
            @Value("${jwt.secret}")
            String secret
    ) {
        secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    /**
     * JWT 토큰에서 UserId를 추출하는 메소드
     * @param token String
     * @return Long
     */
    public Long getUserId(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().get("userId", Long.class);
    }

    /**
     * JWT 토큰에서 Email을 추출하는 메소드
     * @param token String
     * @return String
     */
    public String getEmail(String token) {
        return Jwts.parser().verifyWith(secretKey).build()  // 우리 서버에서 발행한 JWT인지 검증
                .parseSignedClaims(token).getPayload().get("email", String.class); // username 추출
    }

    /**
     * JWT 토큰에서 Role을 추출하는 메소드
     * @param token String
     * @return String
     */
    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().get("role", String.class);
    }

    /**
     * JWT 토큰에서 Nickname을 추출하는 메소드
     * @param token String
     * @return String
     */
    public String getNickname(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().get("nickname", String.class);
    }

    /**
     * JWT 토큰이 유효한지 검증하는 메소드
     * @param token String
     * @throws MalformedJwtException Exp에 대한 정보가 JWT에 존재하지 않을 때
     * @throws ExpiredJwtException JWT 토큰이 만료되었을 때
     */
    public void validateToken(String token) {
        Jws<Claims> jwt = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        Date expiration = jwt.getPayload().getExpiration();

        if (expiration == null) {
            throw new MalformedJwtException("Missing Exp Claim");
        }

        if (expiration.before(new Date())) {
            throw new ExpiredJwtException(null, jwt.getPayload(), "JWT token has expired");
        }
    }

    /**
     * JWT 토큰을 생성하는 메소드
     * @param userId Long
     * @param email String
     * @param role String
     * @param nickname String
     * @return String
     */
    public String generateToken(Long userId, String email, String role, String nickname) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("email", email)
                .claim("role", role)
                .claim("nickname", nickname)
                .issuedAt(new Date(System.currentTimeMillis()))  // 발행시간
                .expiration(new Date(System.currentTimeMillis() + TOKEN_VALID_TIME))  // 소멸 시간
                .signWith(secretKey)  // 암호화
                .compact();
    }
}
