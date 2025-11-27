package com.example.boardserver.board.service.commentService;

import com.example.boardserver.board.converter.CommentConverter;
import com.example.boardserver.board.dto.CommentListResponseDTO;
import com.example.boardserver.board.repository.commentRepository.CommentRepository;
import com.example.boardserver.board.repository.boardRepository.BoardRepository;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.BoardHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentQueryServiceImpl implements CommentQueryService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Override
    public CommentListResponseDTO getCommentsList(Long boardId) {
        boardRepository.findByBoardId(boardId).orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

        return CommentConverter.toCommentListResponseDTO(commentRepository.findAllByBoard_BoardIdOrderByCreatedAtDesc(boardId));
    }
}
