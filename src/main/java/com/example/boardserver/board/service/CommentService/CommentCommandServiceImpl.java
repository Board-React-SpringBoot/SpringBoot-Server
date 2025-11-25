package com.example.boardserver.board.service.CommentService;

import com.example.boardserver.board.converter.CommentConverter;
import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.domain.Comment;
import com.example.boardserver.board.dto.CommentRequestDTO;
import com.example.boardserver.board.dto.CommentResponseDTO;
import com.example.boardserver.board.repository.CommentRepository.CommentRepository;
import com.example.boardserver.board.repository.boardRepository.BoardRepository;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.BoardHandler;
import com.example.boardserver.exception.handler.UserHandler;
import com.example.boardserver.user.domain.User;
import com.example.boardserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Override
    public CommentResponseDTO saveComment(CommentRequestDTO request, Long userId, Long boardId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
        Board board = boardRepository.findByBoardId(boardId).orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

        Comment comment = commentRepository.save(CommentConverter.toComment(request, board, user));
        board.inCreaseCommentCount();
        boardRepository.save(board);

        return CommentConverter.toCommentResponseDTO(comment);
    }
}
