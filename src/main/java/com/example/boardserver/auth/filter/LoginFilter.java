package com.example.boardserver.auth.filter;

import com.example.boardserver.auth.dto.AuthRequestDTO;
import com.example.boardserver.auth.dto.CustomUserDetails;
import com.example.boardserver.auth.jwt.JWTProvider;
import com.example.boardserver.auth.jwt.enums.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;

    @Override
    public Authentication attemptAuthentication (
            HttpServletRequest request, HttpServletResponse response
    ) throws AuthenticationException {
        String email = null;
        String password = null;

        try {
            if ("application/json".equals(request.getContentType())) {  // JSON 기반 로그인 처리
                AuthRequestDTO.LoginRequestDTO loginRequestDTO = new ObjectMapper()
                        .readValue(request.getInputStream(), AuthRequestDTO.LoginRequestDTO.class);

                email = loginRequestDTO.email();
                password = loginRequestDTO.password();
            } else {    // FORM 기반 로그인 처리
                email = request.getParameter("email");
                password = request.getParameter("password");
            }
        } catch (IOException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }

        email = (email != null) ? email : "";
        password = (password != null) ? password : "";

        // 로그인 검증 용 DTO 생성
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        // AuthenticationManager로 Token 전달
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, 
            HttpServletResponse response, 
            FilterChain chain, 
            Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String email = userDetails.getUsername();
        Long userId = userDetails.getUserId();
        String nickname = userDetails.getNickname();

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority grantedAuthority = iterator.next();
        String role = grantedAuthority.getAuthority();

        String access  = jwtProvider.generateToken(userId, email, role, nickname, TokenType.Access);
        String refresh = jwtProvider.generateToken(userId, email, role, nickname, TokenType.Refresh);

        response.addCookie(createCookie(refresh));
        response.addHeader("Authorization", "Bearer " + access);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private Cookie createCookie(String value) {
        Cookie cookie = new Cookie("Refresh", value);
        cookie.setMaxAge(24 * 60 * 60); // Cookie 기한을 24시간으로 설정
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        // cookie.setSecure(true);  // HTTPS 에서만 사용가능

        return cookie;
    }
}
