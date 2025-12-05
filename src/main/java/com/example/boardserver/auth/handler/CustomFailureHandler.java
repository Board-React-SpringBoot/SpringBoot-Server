package com.example.boardserver.auth.handler;

import com.example.boardserver.auth.filter.JWTExceptionFilter;
import com.example.boardserver.common.code.dto.ErrorReasonDTO;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.OAuth2Handler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        /*throw new OAuth2AuthenticationException(exception.getMessage());*/

        if (exception instanceof OAuth2Handler oAuth2Handler) {
            ErrorReasonDTO reason = oAuth2Handler.getErrorReason();
            JWTExceptionFilter.responseError(response, reason);
            return;
        }

        JWTExceptionFilter.responseError(response, ErrorStatus._UNAUTHORIZED);
    }
}
