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
        String token = extractToken(request);

        if (token == null) {
            request.setAttribute("exception", ErrorStatus.JWT_NOT_FOUND.getCode());

            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("JWT 토큰이 확인되었습니다 : " + token);

        // Token 만료기한 검증
        jwtProvider.validateToken(token);

        // 토큰에서 사용자 정보 추출
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

    /**
     * Request에서 Token을 추출하는 메소드
     * @param request HttpServletRequest
     * @return String - JWT Token
     */
    protected String extractToken(HttpServletRequest request) {
        String token = null;

        // Cookie의 Token 추출
        System.out.println(Arrays.toString(request.getCookies()));
        if (request.getCookies() != null) {
            System.out.println("Cookie에서 토큰 추출 중");
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName());

                if ("Authorization".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // Authorization 헤더 검증
        if (token == null) {
            System.out.println("헤더에서 토큰 추출 중");
            String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer ")) {
                token = header.replace("Bearer ", "");
            }
        }

        return token;
    }
}
