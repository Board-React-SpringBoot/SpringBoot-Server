package com.example.boardserver.board.dto.boardDTO;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시물 목록 객체
 * @param boardId Long
 * @param title String
 * @param content String
 * @param boardTitleImage String
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
public record BoardList(
        Long boardId,
        String title,
        String content,
        String boardTitleImage,
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
