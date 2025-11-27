package com.example.boardserver.board.service.commentService;

import com.example.boardserver.board.dto.CommentListResponseDTO;

public interface CommentQueryService {

    /**
     * 해당 게시물의 모든 댓글 리스트를 조회하여 페이지로 분할한 service 메서드
     *
     * @param boardId Long
     * @param page    Integer
     * @return Page<CommentResponseDTO>
     */
    CommentListResponseDTO getCommentsList(Long boardId, Integer page);
}
