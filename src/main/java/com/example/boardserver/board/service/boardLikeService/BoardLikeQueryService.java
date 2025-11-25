package com.example.boardserver.board.service.boardLikeService;

public interface BoardLikeQueryService {

    Boolean findBoardLike(Long userId, Long boardId);
}
