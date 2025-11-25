package com.example.boardserver.board.service.CommentService;

import com.example.boardserver.board.dto.CommentRequestDTO;
import com.example.boardserver.board.dto.CommentResponseDTO;

public interface CommentCommandService {

    /**
     * DB에 Comment를 저장하는 Service 메서드
     * @param request CommentRequestDTO
     * @param userId Long
     * @param boardId Long
     * @return CommentResponseDTO
     */
    CommentResponseDTO saveComment(CommentRequestDTO request, Long userId, Long boardId);
}
