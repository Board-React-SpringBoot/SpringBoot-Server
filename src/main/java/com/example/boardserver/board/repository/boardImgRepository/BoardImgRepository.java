package com.example.boardserver.board.repository.boardImgRepository;

import com.example.boardserver.board.domain.BoardImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardImgRepository extends JpaRepository<BoardImg, Integer> {
}
