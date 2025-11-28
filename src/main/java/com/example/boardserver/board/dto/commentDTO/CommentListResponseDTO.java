package com.example.boardserver.board.dto.commentDTO;

import lombok.Builder;

import java.util.List;

/**
 * 해당 게시물의 모든 댓글을 조회한 Response DTO
 * @param commentList List<CommentResponseDTO>
 * @param listSize Integer
 * @param totalPage Integer
 * @param totalElements Long
 * @param isFirst Boolean
 * @param isLast Boolean
 */
@Builder
public record CommentListResponseDTO(
        List<CommentResponseDTO> commentList,
        Integer listSize,
        Integer totalPage,
        Long totalElements,
        Boolean isFirst,
        Boolean isLast
) {}
