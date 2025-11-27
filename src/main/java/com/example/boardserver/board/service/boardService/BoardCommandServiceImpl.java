package com.example.boardserver.board.service.boardService;

import com.example.boardserver.board.converter.BoardConverter;
import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.dto.BoardRequestDTO;
import com.example.boardserver.board.repository.boardImgRepository.BoardImgRepository;
import com.example.boardserver.board.repository.boardRepository.BoardRepository;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.BoardHandler;
import com.example.boardserver.exception.handler.UserHandler;
import com.example.boardserver.user.domain.User;
import com.example.boardserver.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BoardCommandServiceImpl implements BoardCommandService {

    private final BoardRepository boardRepository;
    private final BoardImgRepository boardImgRepository;
    private final UserRepository userRepository;

    @Override
    public Board saveBoard(BoardRequestDTO request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        return boardRepository.save(BoardConverter.toBoardEntity(request, user));
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId, Long userId) {
        Board board = boardRepository.findByBoardId(boardId).orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        if (!board.getUser().equals(user))
            throw new BoardHandler(ErrorStatus.BOARD_UNAUTHORIZED);

        boardRepository.delete(board);
    }
}
