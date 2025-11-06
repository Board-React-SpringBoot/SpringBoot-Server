package com.example.boardserver.auth.handler;

import com.example.boardserver.auth.filter.JWTExceptionFilter;
import com.example.boardserver.common.code.status.ErrorStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {    // 특정 API에 인증 없이 접근할 경우

        String exception = (String) request.getAttribute("exception");

        if (exception == null) {
            JWTExceptionFilter.responseError(response, ErrorStatus._FORBIDDEN);
            return;
        }

        if (exception.equals(ErrorStatus.JWT_NOT_FOUND.getCode())) {
            JWTExceptionFilter.responseError(response, ErrorStatus.JWT_NOT_FOUND);
        }
    }
}
