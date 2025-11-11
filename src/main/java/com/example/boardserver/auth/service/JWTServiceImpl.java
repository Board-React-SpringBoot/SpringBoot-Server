package com.example.boardserver.auth.service;

import com.example.boardserver.auth.jwt.JWTProvider;
import com.example.boardserver.auth.jwt.enums.TokenType;
import com.example.boardserver.auth.repository.JWTRepository;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.AuthHandler;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    private final JWTProvider jwtProvider;
    private final JWTRepository jwtRepository;

    private static final long REFRESH_TTL = 24 * 60 * 60; // 24시간

    @Override
    public String reissueAccessToken(String refresh) {

        // Refresh 토큰이 존재하는지 확인
        if (!"Refresh".equals(jwtProvider.getCategory(refresh))) {
            throw new AuthHandler(ErrorStatus.REFRESH_NOT_FOUND);
        }

        // Refresh 토큰 만료 여부 확인
        try {
            jwtProvider.validateToken(refresh);
        } catch (ExpiredJwtException e) {
            throw new AuthHandler(ErrorStatus.JWT_EXPIRED);
        }

        // Refresh 토큰에서 정보 추출
        Long userId = jwtProvider.getUserId(refresh);
        String email = jwtProvider.getEmail(refresh);
        String role = jwtProvider.getRole(refresh);
        String nickname = jwtProvider.getNickname(refresh);

        // 새로운 Access 토큰 발급
        return jwtProvider.generateToken(userId, email, role, nickname, TokenType.Access);
    }

    @Override
    public String rotateRefreshToken(String refresh) {
        if (!"Refresh".equals(jwtProvider.getCategory(refresh))) {
            throw new AuthHandler(ErrorStatus.REFRESH_NOT_FOUND);
        }

        try {
            jwtProvider.validateToken(refresh);
        } catch (ExpiredJwtException e) {
            throw new AuthHandler(ErrorStatus.JWT_EXPIRED);
        }

        Long userId = jwtProvider.getUserId(refresh);
        String email = jwtProvider.getEmail(refresh);
        String role = jwtProvider.getRole(refresh);
        String nickname = jwtProvider.getNickname(refresh);

        return jwtProvider.generateToken(userId, email, role, nickname, TokenType.Refresh);
    }

    @Override
    public void saveRefreshToken(String refresh, String device) {
        jwtRepository.save(
                jwtProvider.getUserId(refresh),
                device,
                refresh,
                Duration.ofDays(REFRESH_TTL)
        );
    }

    @Override
    public Boolean existsRefreshToken(String refresh, String device) {
        return jwtRepository.exists(jwtProvider.getUserId(refresh), device);
    }

    @Override
    public void deleteRefreshToken(String refresh,String device) {
        jwtRepository.deleteRefresh(jwtProvider.getUserId(refresh), device);
    }

    @Override
    public void deleteAllRefreshTokens(String refresh) {
        jwtRepository.deleteAllRefresh(jwtProvider.getUserId(refresh));
    }
}
