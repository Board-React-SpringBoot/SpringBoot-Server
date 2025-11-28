package com.example.boardserver.board.service.boardService;

import com.example.boardserver.board.converter.BoardConverter;
import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.dto.boardDTO.BoardResponseDetailDTO;
import com.example.boardserver.board.repository.boardRepository.BoardRepository;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.BoardHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardQueryServiceImpl implements BoardQueryService {

    private final BoardRepository boardRepository;

    @Override
    public BoardResponseDetailDTO getBoardDetail(Long boardId) {

        Board board = boardRepository.findByBoardId(boardId).orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));
        board.inCreaseViewCount();
        boardRepository.save(board);

        return BoardConverter.toBoardResponseDetailDTO(board);
    }
}
