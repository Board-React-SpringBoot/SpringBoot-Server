package com.example.boardserver.board.dto.boardDTO;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시물 상세보기 Response DTO
 * @param boardId Long
 * @param title String
 * @param content String
 * @param boardImageList String[]
 * @param likeCount Integer
 * @param commentCount String
 * @param viewCount String
 * @param createdAt LocalDateTime
 * @param updatedAt LocalDateTime
 * @param userId Long
 * @param email String
 * @param nickname String
 * @param profile String
 */
@Builder
public record BoardResponseDetailDTO(
        Long boardId,
        String title,
        String content,
        List<String> boardImageList,
        Integer likeCount,
        Integer commentCount,
        Integer viewCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long userId,
        String email,
        String nickname,
        String profile
        ) {

}
