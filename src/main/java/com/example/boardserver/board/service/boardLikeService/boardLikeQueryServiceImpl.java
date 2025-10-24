package com.example.boardserver.board.service.boardLikeService;

import com.example.boardserver.board.domain.BoardLikeId;
import com.example.boardserver.board.repository.BoardLikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class boardLikeQueryServiceImpl implements boardLikeQueryService {

    private final BoardLikeRepository boardLikeRepository;

    @Transactional()
    public void findBoardLike(Long userId, Long boardId) {
        System.out.println(boardLikeRepository.findByUser_UserIdAndBoard_BoardId(userId, boardId).isPresent());

        // Transactional을 사용하지 않으면 LazyFetch에 의해 실제 Board 엔티티는 조회하지 않으므로 오류 발생
        System.out.println(boardLikeRepository.findByUserIdAndBoardId(userId, boardId).get().getBoard().getTitle());

        System.out.println(boardLikeRepository.findByBoardLikeId(new BoardLikeId(userId, boardId)).get().getUser().getNickname());
    }
}
