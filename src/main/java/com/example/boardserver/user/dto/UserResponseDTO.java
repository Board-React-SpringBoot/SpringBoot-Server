package com.example.boardserver.user.dto;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 특정 유저 상세 정보 Response DTO
 * @param userId Long
 * @param email String
 * @param nickname String
 * @param profile String
 * @param createdAt LocalDateTime
 * @param updatedAt LocalDateTime
 */
@Builder
public record UserResponseDTO(
        Long userId,
        String email,
        String nickname,
        String profile,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
