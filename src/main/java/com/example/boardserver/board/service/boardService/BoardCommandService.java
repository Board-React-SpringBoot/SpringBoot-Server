package com.example.boardserver.board.service.boardService;

import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.dto.BoardRequestDTO;

public interface BoardCommandService {

    /**
     * 게시물을 DB에 저장하는 Service 메서드
     * @param request BoardRequestDTO
     * @param userId Long
     * @return Board
     */
    Board saveBoard(BoardRequestDTO request, Long userId);

    /**
     * 게시물을 삭제하는 Service 메서드
     * @param boardId Long
     * @param userId Long
     */
    void deleteBoard(Long boardId, Long userId);
}
