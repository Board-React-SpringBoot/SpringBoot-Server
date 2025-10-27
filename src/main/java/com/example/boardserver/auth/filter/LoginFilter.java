package com.example.boardserver.auth.filter;

import com.example.boardserver.auth.dto.AuthRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

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

        System.out.println("email: " + email);
        System.out.println("password: " + password);

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
        // TODO : JWT 토큰 발급 구현
        throw new UnsupportedOperationException("JWT 발급 로직 미구현");
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) {
        // TODO : 로그인 실패 코드 구현
        throw new UnsupportedOperationException("로그인 실패 로직 미구현");
    }
}
