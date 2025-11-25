package com.example.boardserver.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

/**
 * 게시물 작성 Request DTO
 * @param title String
 * @param content String
 * @param boardMainImg String
 * @param boardImageList String[]
 */
@Builder
public record BoardRequestDTO(
        @NotBlank
        String title,
        @NotBlank
        String content,
        String boardMainImg,
        List<String> boardImageList
) {
}
