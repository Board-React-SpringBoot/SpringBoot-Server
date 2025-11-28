package com.example.boardserver.board.repository.boardRepository;

import com.example.boardserver.board.domain.Board;

import java.util.List;

public interface BoardRepositoryCustom {

    /**
     * 주간 Tops 게시물 리스트 조회 Repository 메서드
     * @return List<Board>
     */
    List<Board> findWeeklyTop3BoardList();
}
