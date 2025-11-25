package com.example.boardserver.board.repository.BoardLikeRepository;

import com.example.boardserver.board.domain.BoardLikeId;
import com.example.boardserver.board.dto.BoardLikeInfoDTO;

import java.util.Optional;

public interface BoardLikeRepositoryCustom {

    Optional<BoardLikeInfoDTO> findBoardLike(BoardLikeId boardLikeId);
}
