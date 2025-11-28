package com.example.boardserver.board.dto.commentDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * 특정 게시물에 댓글을 달기 위한 Request DTO
 * @param content String
 */
@Builder
public record CommentRequestDTO(
        @NotBlank
        String content
) {
}
