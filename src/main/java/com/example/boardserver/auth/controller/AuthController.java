package com.example.boardserver.auth.controller;

import com.example.boardserver.auth.dto.AuthRequestDTO;
import com.example.boardserver.auth.dto.JoinRequestDTO;
import com.example.boardserver.auth.dto.JoinResponseDTO;
import com.example.boardserver.auth.service.AuthCommandService;
import com.example.boardserver.auth.service.JWTService;
import com.example.boardserver.common.ApiResponse;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.common.util.DeviceUtils;
import com.example.boardserver.exception.handler.AuthHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {

    private final AuthCommandService authCommandService;
    private final JWTService jwtService;

    @PostMapping("/join")
    public ApiResponse<JoinResponseDTO> join(@RequestBody @Valid JoinRequestDTO request) {
        return ApiResponse.onSuccess(authCommandService.saveUser(request));
    }

    @PostMapping("/login")
    public ApiResponse<AuthRequestDTO.LoginRequestDTO> login(@RequestBody AuthRequestDTO.LoginRequestDTO request) {
        return ApiResponse.onSuccess(request);
    }

    @PostMapping("/naver")
    public String naver() {
        return "http://localhost:8080/api/v1/auth/oauth2/naver";
    }

    @PostMapping("/google")
    public String google() {
        return "http://localhost:8080/api/v1/auth/oauth2/google";
    }

    @PostMapping("/github")
    public String github() {
        return "http://localhost:8080/api/v1/auth/oauth2/github";
    }

    @PostMapping("/kakao")
    public String kakao() {
        return "http://localhost:8080/api/v1/auth/oauth2/kakao";
    }

    @GetMapping("/reissue")
    public ApiResponse<String> reissue(HttpServletRequest request, HttpServletResponse response) {
        
        // 쿠키에서 Refresh 토큰 추출
        String refresh = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> "Refresh".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new AuthHandler(ErrorStatus.REFRESH_NOT_FOUND));

        String deviceType = DeviceUtils.getDeviceType(request);
        if (!jwtService.existsRefreshToken(refresh, deviceType)) {
            throw new AuthHandler(ErrorStatus.REFRESH_NOT_FOUND);
        }
        
        String newAccess = jwtService.reissueAccessToken(refresh);
        String newRefresh = jwtService.rotateRefreshToken(refresh);

        jwtService.saveRefreshToken(newRefresh, deviceType);
        
        response.setHeader("Authorization", "Bearer " + newAccess);
        response.addCookie(createCookie(newRefresh));
        return ApiResponse.onSuccess("새로운 Access 토큰 발급");
    }

    private Cookie createCookie(String value) {
       Cookie cookie = new Cookie("Refresh", value);
       cookie.setMaxAge(24 * 60 * 60);
       cookie.setPath("/");
       cookie.setHttpOnly(true);
       // cookie.setSecure(true);

        return cookie;
    }
}
