package com.example.boardserver.auth.dto;

public record AuthRequestDTO() {

    /**
     * 일반 로그인 용 DTO
     * @param email String
     * @param password String
     */
    public record LoginRequestDTO(
            String email,
            String password
    ) {}
}
