package com.example.boardserver.board.service.boardLikeService;

import com.example.boardserver.board.domain.BoardLikeId;
import com.example.boardserver.board.repository.boardLikeRepository.BoardLikeRepository;
import com.example.boardserver.board.repository.boardRepository.BoardRepository;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.BoardHandler;
import com.example.boardserver.exception.handler.UserHandler;
import com.example.boardserver.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BoardLikeQueryServiceImpl implements BoardLikeQueryService {

    private final BoardLikeRepository boardLikeRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional()
    public Boolean findBoardLike(Long userId, Long boardId) {
        boardRepository.findByBoardId(boardId).orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));
        userRepository.findByUserId(userId).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        return boardLikeRepository.existsByBoardLikeId(new BoardLikeId(userId, boardId));
    }
}
