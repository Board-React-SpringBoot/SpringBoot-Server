package com.example.boardserver.board.service.boardLikeService;

import com.example.boardserver.board.domain.BoardLikeId;
import com.example.boardserver.board.dto.boardDTO.BoardResponseDetailDTO;

public interface BoardLikeCommandService {

    /**
     * boardId와 userId로 해당 게시물의 좋아요를 변경하는 Service 메서드
     * @param boardLikeId BoardLikeId
     * @return BoardLike
     */
    BoardResponseDetailDTO toggleBoardLike(BoardLikeId boardLikeId);
}
