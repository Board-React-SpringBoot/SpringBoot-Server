package com.example.boardserver.board.service.commentService;

import com.example.boardserver.board.converter.CommentConverter;
import com.example.boardserver.board.dto.commentDTO.CommentListResponseDTO;
import com.example.boardserver.board.repository.commentRepository.CommentRepository;
import com.example.boardserver.board.repository.boardRepository.BoardRepository;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.BoardHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentQueryServiceImpl implements CommentQueryService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Override
    public CommentListResponseDTO getCommentsList(Long boardId, Integer page) {
        boardRepository.findByBoardId(boardId).orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());

        return CommentConverter.toCommentListResponseDTO(
                commentRepository.findByBoard_BoardId(boardId, pageable)
        );
    }
}
