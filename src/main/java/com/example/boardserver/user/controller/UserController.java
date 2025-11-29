package com.example.boardserver.user.controller;

import com.example.boardserver.auth.dto.CustomUserDetails;
import com.example.boardserver.common.ApiResponse;
import com.example.boardserver.user.converter.UserConverter;
import com.example.boardserver.user.dto.UserMyPageResponseDTO;
import com.example.boardserver.user.dto.UserResponseDTO;
import com.example.boardserver.user.service.UserQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "유저 관련 API")
public class UserController {

    private final UserQueryService userQueryService;

    @GetMapping("/test")
    public ApiResponse<Map<String, Object>> test() {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long userId = user.getUserId();
        String email = user.getUsername();
        String password = user.getPassword();
        String role = user.getAuthorities().iterator().next().getAuthority();
        String nickname = user.getNickname();

        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("email", email);
        result.put("password", password);
        result.put("role", role);
        result.put("nickname", nickname);

        return ApiResponse.onSuccess(result);
    }

    @GetMapping()
    public ApiResponse<UserMyPageResponseDTO> getDetailUser() {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ApiResponse.onSuccess(
                UserConverter.toUserMyPageResponseDTO(
                        userQueryService.getDetailUser(user.getUserId())
                )
        );
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponseDTO> getUser(
            @PathVariable("userId") Long userId
    ) {
        return ApiResponse.onSuccess(
                UserConverter.toUserResponseDTO(
                        userQueryService.getDetailUser(userId)
                )
        );
    }
}
