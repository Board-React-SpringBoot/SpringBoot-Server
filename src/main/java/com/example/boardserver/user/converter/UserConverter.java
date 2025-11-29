package com.example.boardserver.user.converter;

import com.example.boardserver.user.domain.User;
import com.example.boardserver.user.dto.UserMyPageResponseDTO;
import com.example.boardserver.user.dto.UserResponseDTO;

import java.util.Optional;

public class UserConverter {

    /**
     * User 엔티티를 UserMyPageResponseDTO로 변환하는 메소드
     * @param user User
     * @return UserMyPageResponseDTO
     */
    public static UserMyPageResponseDTO toUserMyPageResponseDTO(User user) {
        return UserMyPageResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profile(
                        Optional.ofNullable(user.getProfile())
                                .filter(s -> !s.isEmpty())
                                .orElse(null)
                )
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static UserResponseDTO toUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profile(
                        Optional.ofNullable(user.getProfile())
                                .filter(s -> !s.isEmpty())
                                .orElse(null)
                )
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
