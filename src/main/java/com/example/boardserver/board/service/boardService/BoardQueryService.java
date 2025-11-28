package com.example.boardserver.board.service.boardService;

import com.example.boardserver.board.dto.boardDTO.BoardListPageResponseDTO;
import com.example.boardserver.board.dto.boardDTO.BoardResponseDetailDTO;

public interface BoardQueryService {

    /**
     * BoardId로 해당 게시물의 상세 정보를 조회하는 Service 메소드
     * @param boardId Long
     * @return BoardResponseDetailDTO
     */
    BoardResponseDetailDTO getBoardDetail(Long boardId);

    /**
     * 최신 게시물 리스트를 조회하는 Service 메서드
     * @param page Integer
     * @return BoardListPageResponseDTO
     */
    BoardListPageResponseDTO getBoardLatestList(Integer page);
}
