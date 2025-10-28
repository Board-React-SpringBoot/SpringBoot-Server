package com.example.boardserver.auth.dto;

import com.example.boardserver.user.domain.enums.RoleType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record JoinResponseDTO(
        Long userid,
        String email,
        String nickname,
        String profile,
        RoleType role,
        boolean social,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
