package com.example.boardserver.auth.controller;

import com.example.boardserver.auth.dto.AuthRequestDTO;
import com.example.boardserver.auth.dto.JoinRequestDTO;
import com.example.boardserver.auth.dto.JoinResponseDTO;
import com.example.boardserver.auth.service.AuthCommandService;
import com.example.boardserver.common.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {

    private final AuthCommandService authCommandService;

    @PostMapping("/join")
    public ApiResponse<JoinResponseDTO> join(@RequestBody JoinRequestDTO request) {
        return ApiResponse.onSuccess(authCommandService.saveUser(request));
    }

    @PostMapping("/login")
    public ApiResponse<AuthRequestDTO.LoginRequestDTO> login(@RequestBody AuthRequestDTO.LoginRequestDTO request) {
        return ApiResponse.onSuccess(request);
    }
}
