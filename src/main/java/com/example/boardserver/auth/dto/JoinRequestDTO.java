package com.example.boardserver.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record JoinRequestDTO (
        @NotBlank
        String nickname,
        @NotBlank @Email
        String email,
        @NotBlank @Size(min = 2, max = 16)
        String password,
        String profile
) {
}
