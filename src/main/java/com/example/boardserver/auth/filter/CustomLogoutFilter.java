package com.example.boardserver.auth.filter;

import com.example.boardserver.auth.jwt.JWTProvider;
import com.example.boardserver.auth.service.JWTService;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.common.util.DeviceUtils;
import com.example.boardserver.exception.handler.AuthHandler;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilter {

    private final JWTProvider jwtProvider;
    private final JWTService jwtService;

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {
        doFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, filterChain);
    }

    private void doFilter(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {

        if (!request.getRequestURI().matches("^/api/v1/auth/logout$")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!"POST".equals(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String deviceType = DeviceUtils.getDeviceType(request);
        String refresh;

        refresh = validateRefreshToken(request, deviceType);
        jwtService.deleteRefreshToken(refresh, deviceType);

        Cookie cookie = new Cookie("Refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private String validateRefreshToken(HttpServletRequest request, String deviceType) {
        String refresh = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> "Refresh".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new AuthHandler(ErrorStatus.REFRESH_NOT_FOUND));

        jwtProvider.validateToken(refresh);
        if (!"Refresh".equals(jwtProvider.getCategory(refresh))) {
            throw new AuthHandler(ErrorStatus.REFRESH_NOT_FOUND);
        }
        if (!jwtService.existsRefreshToken(refresh, deviceType)) {
            throw new AuthHandler(ErrorStatus.INVALID_REFRESH_TOKEN);
        }

        return refresh;
    }
}
