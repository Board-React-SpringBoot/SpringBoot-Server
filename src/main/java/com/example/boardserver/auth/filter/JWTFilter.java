package com.example.boardserver.auth.filter;

import com.example.boardserver.auth.dto.CustomUserDetails;
import com.example.boardserver.auth.jwt.JWTProvider;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.user.domain.enums.RoleType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Token 추출
        String accessToken = request.getHeader("Authorization");
        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            request.setAttribute("exception", ErrorStatus.JWT_NOT_FOUND.getCode());
            filterChain.doFilter(request, response);
            return;
        }

        accessToken = accessToken.substring("Bearer ".length());
        System.out.println("JWT 토큰이 확인되었습니다 : " + accessToken);

        // Token 만료기한 검증
        jwtProvider.validateToken(accessToken);

        // 토큰에서 사용자 정보 추출
        CustomUserDetails loginUser = CustomUserDetails.builder()
                .userId(jwtProvider.getUserId(accessToken))
                .role(RoleType.toRoleType(jwtProvider.getRole(accessToken)))
                .email(jwtProvider.getEmail(accessToken))
                .nickname(jwtProvider.getNickname(accessToken))
                .build();

        System.out.println("로그인 유저 : " + loginUser);

        // 세션에 로그인 유저 등록
        Authentication authToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
