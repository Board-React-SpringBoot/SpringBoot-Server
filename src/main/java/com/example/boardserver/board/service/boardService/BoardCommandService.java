package com.example.boardserver.board.service.boardService;

import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.dto.BoardRequestDTO;

public interface BoardCommandService {

    Board saveBoard(BoardRequestDTO request, Long userId);
}
