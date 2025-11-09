package com.example.boardserver.auth.service;

import com.example.boardserver.auth.jwt.JWTProvider;
import com.example.boardserver.auth.jwt.enums.TokenType;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.AuthHandler;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    private final JWTProvider jwtProvider;

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
}
