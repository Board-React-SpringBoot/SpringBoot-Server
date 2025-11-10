package com.example.boardserver.auth.handler;

import com.example.boardserver.auth.dto.CustomUserDetails;
import com.example.boardserver.auth.jwt.JWTProvider;
import com.example.boardserver.auth.jwt.enums.TokenType;
import com.example.boardserver.auth.service.JWTService;
import com.example.boardserver.common.util.DeviceUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTProvider jwtProvider;
    private final JWTService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        CustomUserDetails oAuth2User = (CustomUserDetails) authentication.getPrincipal();

        Long userId = oAuth2User.getUserId();
        String email = oAuth2User.getUsername();
        String nickname = oAuth2User.getNickname();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority authority = iterator.next();
        String role = authority.getAuthority();
        
        String token = jwtProvider.generateToken(userId, email, role, nickname, TokenType.Refresh);

        String deviceType = DeviceUtils.getDeviceType(request);
        jwtService.saveRefreshToken(token, deviceType);
        
        response.addCookie(createCookie(token));
        // TODO : 나중에 프론트엔드 메인 URL로 변경하기
        response.sendRedirect("http://localhost:8080/");
    }
    
    private Cookie createCookie(String value) {
        Cookie cookie = new Cookie("Refresh", value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        // cookie.setSecure(true);  // HTTPS 에서만 사용가능

        return cookie;
    }
}
