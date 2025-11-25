package com.example.boardserver.board.dto;

import lombok.Builder;

import java.util.List;

/**
 * 해당 게시물의 모든 댓글을 조회한 Response DTO
 * @param commentList List<CommentResponseDTO>
 */
@Builder
public record CommentListResponseDTO(
        List<CommentResponseDTO> commentList
) {}
