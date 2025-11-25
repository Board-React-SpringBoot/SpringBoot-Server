package com.example.boardserver.board.service.CommentService;

import com.example.boardserver.board.dto.CommentListResponseDTO;

public interface CommentQueryService {

    /**
     * 해당 게시물의 모든 댓글 리스트를 조회하는 service 메서드
     * @param boardId Long
     * @return List<CommentResponseDTO>
     */
    CommentListResponseDTO getCommentsList(Long boardId);
}
