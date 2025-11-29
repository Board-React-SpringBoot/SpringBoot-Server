package com.example.boardserver.board.repository.boardRepository;

import com.example.boardserver.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {

    /**
     * 주간 Tops 게시물 리스트 조회 Repository 메서드
     * @return List<Board>
     */
    List<Board> findWeeklyTop3BoardList();

    /**
     * 제목에 특정 키워드를 포함한 모든 게시물 리스트를 조회하는 Repository 메서드
     * @param keyword String
     * @param pageable Pageable
     * @return Page<Board>
     */
    Page<Board> searchBoardList(String keyword, Pageable pageable);
}
