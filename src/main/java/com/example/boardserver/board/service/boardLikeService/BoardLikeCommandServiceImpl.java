package com.example.boardserver.board.service.boardLikeService;

import com.example.boardserver.board.converter.BoardConverter;
import com.example.boardserver.board.converter.BoardLikeConverter;
import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.domain.BoardLike;
import com.example.boardserver.board.domain.BoardLikeId;
import com.example.boardserver.board.dto.BoardResponseDetailDTO;
import com.example.boardserver.board.repository.BoardLikeRepository.BoardLikeRepository;
import com.example.boardserver.board.repository.boardRepository.BoardRepository;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.BoardHandler;
import com.example.boardserver.exception.handler.UserHandler;
import com.example.boardserver.user.domain.User;
import com.example.boardserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardLikeCommandServiceImpl implements BoardLikeCommandService {

    private final BoardLikeRepository boardLikeRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Override
    public BoardResponseDetailDTO toggleBoardLike(BoardLikeId boardLikeId) {
        User user = userRepository.findByUserId(boardLikeId.getUserId()).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
        Board board = boardRepository.findByBoardId(boardLikeId.getBoardId()).orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

        Optional<BoardLike> boardLike = boardLikeRepository.findByBoardLikeId(boardLikeId);

        // 이미 좋아요를 눌렀을 경우 -> DB에서 좋아요 삭제 -> 해당 게시물의 좋아요 수 -1
        if (boardLike.isPresent()) {
            boardLikeRepository.delete(boardLike.get());
            board.decreaseLikeCount();
            boardRepository.save(board);
        }

        // 좋아요가 존재하지 않을 경우 -> DB에서 좋아요 추가 -> 해당 게시물의 좋아요 수 +1
        if (boardLike.isEmpty()) {
            boardLikeRepository.save(BoardLikeConverter.toBoardLike(user, board));
            board.inCreaseLikeCount();
            boardRepository.save(board);
        }

        return BoardConverter.toBoardResponseDetailDTO(board);
    }
}
