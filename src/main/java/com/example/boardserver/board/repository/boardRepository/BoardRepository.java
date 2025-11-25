package com.example.boardserver.board.repository.boardRepository;

import com.example.boardserver.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    /**
     * BoardId로 DB에서 해당 게시물 Entity를 조회하는 Repository 메서드
     * @param boardId Long
     * @return Optional<Board>
     */
    Optional<Board> findByBoardId(Long boardId);
}
