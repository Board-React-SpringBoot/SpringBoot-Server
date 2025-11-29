package com.example.boardserver.board.service.boardService;

import com.example.boardserver.board.converter.BoardConverter;
import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.dto.boardDTO.BoardListPageResponseDTO;
import com.example.boardserver.board.dto.boardDTO.BoardListResponseDTO;
import com.example.boardserver.board.dto.boardDTO.BoardResponseDetailDTO;
import com.example.boardserver.board.repository.boardRepository.BoardRepository;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.BoardHandler;
import com.example.boardserver.keyword.converter.KeywordConverter;
import com.example.boardserver.keyword.domain.Keyword;
import com.example.boardserver.keyword.repository.KeywordRepository;
import com.example.boardserver.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardQueryServiceImpl implements BoardQueryService {

    private final BoardRepository boardRepository;
    private final KeywordRepository keywordRepository;
    private final UserRepository userRepository;

    @Override
    public BoardResponseDetailDTO getBoardDetail(Long boardId) {

        Board board = boardRepository.findByBoardId(boardId).orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));
        board.increaseViewCount();
        boardRepository.save(board);

        return BoardConverter.toBoardResponseDetailDTO(board);
    }

    @Override
    public BoardListPageResponseDTO getBoardLatestList(Integer page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("createdAt").descending());

        return BoardConverter.toBoardListPageDTO(
                boardRepository.findAll(pageable)
        );
    }

    @Override
    public BoardListResponseDTO getBoardTop3List() {
        return BoardConverter.toBoardListResponseDTO(
                boardRepository.findWeeklyTop3BoardList()
        );
    }

    @Override
    @Transactional
    public BoardListPageResponseDTO getSearchBoardList(String keyword, Integer page) {
        Pageable pageable = PageRequest.of(page, 5);

        Optional<Keyword> word = keywordRepository.findByKeyword(keyword);

        if (word.isPresent()) {
            word.get().increaseCount();
            keywordRepository.save(word.get());
        } else {
            keywordRepository.save(KeywordConverter.toKeyword(keyword));
        }

        return BoardConverter.toBoardListPageDTO(
                boardRepository.searchBoardList(keyword, pageable)
        );
    }

    @Override
    public BoardListPageResponseDTO getBoardListByUserId(Long userId, Integer page) {
        userRepository.findById(userId).orElseThrow(() -> new BoardHandler(ErrorStatus.USER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, 5, Sort.by("createdAt").descending());

        return BoardConverter.toBoardListPageDTO(
                boardRepository.findAllByUser_UserId(userId, pageable)
        );
    }
}
