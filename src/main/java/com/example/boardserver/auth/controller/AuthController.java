package com.example.boardserver.auth.controller;

import com.example.boardserver.auth.dto.JoinRequestDTO;
import com.example.boardserver.auth.dto.JoinResponseDTO;
import com.example.boardserver.auth.service.AuthCommandService;
import com.example.boardserver.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthCommandService authCommandService;

    @PostMapping("/join")
    public ApiResponse<JoinResponseDTO> join(@RequestBody JoinRequestDTO request) {
        return ApiResponse.onSuccess(authCommandService.saveUser(request));
    }
}
