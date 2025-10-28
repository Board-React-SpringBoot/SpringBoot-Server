package com.example.boardserver.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record JoinRequestDTO (
        @NotBlank
        String nickname,
        @NotBlank
        String email,
        @NotBlank
        String password,
        String profile
) {
}
