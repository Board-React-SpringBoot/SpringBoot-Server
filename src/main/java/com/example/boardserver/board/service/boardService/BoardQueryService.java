package com.example.boardserver.board.service.boardService;

import com.example.boardserver.board.dto.boardDTO.BoardResponseDetailDTO;

public interface BoardQueryService {

    /**
     * BoardId로 해당 게시물의 상세 정보를 조회하는 Service 메소드
     * @param boardId Long
     * @return BoardResponseDetailDTO
     */
    BoardResponseDetailDTO getBoardDetail(Long boardId);
}
