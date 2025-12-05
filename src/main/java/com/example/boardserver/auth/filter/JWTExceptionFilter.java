package com.example.boardserver.auth.filter;

import com.example.boardserver.common.ApiResponse;
import com.example.boardserver.common.code.dto.ErrorReasonDTO;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.AuthHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JWTExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        
        // JWTFilter에서 검증 중 발생한 예외 처리
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.info("JWT 토큰이 만료되었습니다.");
            responseError(response, ErrorStatus.JWT_EXPIRED);
        } catch (MalformedJwtException e) {
            log.info("JWT 토큰의 형식이 잘못되었습니다.");
            responseError(response, ErrorStatus.JWT_MALFORMED);
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT 토큰입니다.");
            responseError(response, ErrorStatus.JWT_UNSUPPORTED);
        } catch (SignatureException e) {
            log.info("JWT 토큰의 서명이 잘못되었습니다.");
            responseError(response, ErrorStatus.JWT_SIGNATURE_FAILED);
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
            responseError(response, ErrorStatus.JWT_NOT_FOUND);
        } catch (AuthHandler e) {
            log.info(e.getErrorReason().message());
            responseError(response, (ErrorStatus) e.getCode());
        }
    }

    /**
     * JWT 인증 중 발생한 예외를 클라이언트에게 응답으로 보내는 메소드
     * @param response HttpServletResponse
     * @param errorStatus ErrorStatus
     * @throws IOException IOException
     */
    public static void responseError(HttpServletResponse response, ErrorStatus errorStatus) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorStatus.getHttpStatus().value());

        ObjectMapper mapper = new ObjectMapper();
        ApiResponse<String> failureResponse = ApiResponse.onFailure(errorStatus.getCode(), errorStatus.getMessage(), null);

        String body = mapper.writeValueAsString(failureResponse);
        response.getWriter().write(body);
        // ApiResponse.onFailure(errorStatus.getCode(), errorStatus.getMessage(), null);
    }

    /**
     * OAuth2 인증 중 발생한 예외를 클라이언트에게 응답으로 보내는 메소드
     * @param response HttpServletResponse
     * @param reason ErrorReasonDTO
     * @throws IOException IOException
     */
    public static void responseError(HttpServletResponse response, ErrorReasonDTO reason) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        HttpStatus httpStatus = reason.httpStatus() != null ? reason.httpStatus() : HttpStatus.BAD_REQUEST;
        response.setStatus(httpStatus.value());

        ObjectMapper mapper = new ObjectMapper();
        ApiResponse<String> failureResponse = ApiResponse.onFailure(reason.code(), reason.message(), null);

        String body = mapper.writeValueAsString(failureResponse);
        response.getWriter().write(body);
    }
}
