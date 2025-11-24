package com.example.boardserver.board.repository.boardRepository;

import com.example.boardserver.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
