package com.example.boardserver.user.dto;

import com.example.boardserver.user.domain.enums.RoleType;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 본인의 유저 상세 정보 Response DTO
 * @param userId Long
 * @param email String
 * @param nickname String
 * @param profile String
 * @param role RoleType
 * @param createdAt LocalDateTime
 * @param updatedAt LocalDateTime
 */
@Builder
public record UserMyPageResponseDTO(
        Long userId,
        String email,
        String nickname,
        String profile,
        RoleType role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
