package com.example.boardserver.user.converter;

import com.example.boardserver.user.domain.User;
import com.example.boardserver.user.dto.UserResponse;

import java.util.Optional;

public class UserResponseConverter {

    /**
     * User 엔티티를 UserMyPageDTO로 변환하는 메소드
     * @param user User
     * @return UserMyPageDTO
     */
    public static UserResponse.UserMyPageDTO toUserMyPageDTO(User user) {
        return UserResponse.UserMyPageDTO.builder()
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
}
