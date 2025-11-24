package com.example.boardserver.board.service.boardService;

import com.example.boardserver.board.converter.BoardConverter;
import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.domain.BoardImg;
import com.example.boardserver.board.dto.BoardRequestDTO;
import com.example.boardserver.board.repository.boardImgRepository.BoardImgRepository;
import com.example.boardserver.board.repository.boardRepository.BoardRepository;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.UserHandler;
import com.example.boardserver.user.domain.User;
import com.example.boardserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardCommandServiceImpl implements BoardCommandService {

    private final BoardRepository boardRepository;
    private final BoardImgRepository boardImgRepository;
    private final UserRepository userRepository;

    @Override
    public Board saveBoard(BoardRequestDTO request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
/*        Board newBoard = boardRepository.save(BoardConverter.toBoardEntity(request, user));

        List<BoardImg> boardImgList = BoardConverter.toBoardImgEntityList(request, newBoard);
        boardImgRepository.saveAll(boardImgList);
        newBoard.getBoardImgList().addAll(boardImgList);*/

        return boardRepository.save(BoardConverter.toBoardEntity(request, user));
    }
}
