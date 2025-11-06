package com.example.boardserver.auth.handler;

import com.example.boardserver.auth.filter.JWTExceptionFilter;
import com.example.boardserver.common.code.status.ErrorStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {

        // 권한이 없어 특정 APi에 대한 요청이 거부될 경우
        JWTExceptionFilter.responseError(response, ErrorStatus._FORBIDDEN);
    }
}
