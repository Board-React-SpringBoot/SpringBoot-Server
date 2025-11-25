package com.example.boardserver.board.dto;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 댓글 ResponseDTO
 * @param commentId Long
 * @param userId Long
 * @param nickname String
 * @param profile String
 * @param content String
 * @param createdAt LocalDateTime
 * @param updatedAt LocalDateTime
 */
@Builder
public record CommentResponseDTO(
        Long commentId,
        Long userId,
        String nickname,
        String profile,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
