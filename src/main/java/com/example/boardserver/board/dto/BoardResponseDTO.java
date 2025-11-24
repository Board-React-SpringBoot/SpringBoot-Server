package com.example.boardserver.board.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시물 작성 후 결과 Response DTO
 * @param boardId Long
 * @param userId Long
 * @param title String
 * @param content String
 * @param mainImg String
 * @param boardImageList String[]
 * @param likeCount Integer
 * @param commentCount String
 * @param viewCount String
 * @param createdAt LocalDateTime
 * @param updatedAt LocalDateTime
 */
@Builder
public record BoardResponseDTO(
        Long boardId,
        Long userId,
        String title,
        String content,
        String mainImg,
        List<String> boardImageList,
        Integer likeCount,
        Integer commentCount,
        Integer viewCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
