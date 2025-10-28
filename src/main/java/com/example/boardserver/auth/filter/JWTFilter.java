package com.example.boardserver.auth.filter;

import com.example.boardserver.auth.dto.CustomUserDetails;
import com.example.boardserver.auth.jwt.JWTProvider;
import com.example.boardserver.user.domain.enums.RoleType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("JWT 토큰이 없습니다.");
            filterChain.doFilter(request, response);

            return;
        }

        System.out.println("JWT 토큰이 존재합니다.");

        String token = authorization.split(" ")[1];

        // Token 만료기한 검증
        if (jwtProvider.isExpired(token)) {
            System.out.println("JWT 토큰이 만료되었습니다.");
            filterChain.doFilter(request, response);

            return;
        }

        CustomUserDetails loginUser = CustomUserDetails.builder()
                .userId(jwtProvider.getUserId(token))
                .role(RoleType.toRoleType(jwtProvider.getRole(token)))
                .email(jwtProvider.getEmail(token))
                .nickname(jwtProvider.getNickname(token))
                .build();

        System.out.println("로그인 유저 : " + loginUser);

        // 세션에 로그인 유저 등록
        Authentication authToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
