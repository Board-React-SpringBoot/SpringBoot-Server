package com.example.boardserver.board.repository.BoardLikeRepository;

import com.example.boardserver.board.domain.BoardLike;
import com.example.boardserver.board.domain.BoardLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikeId> {

    /**
     * boardId와 userId를 통해 해당 게시물의 좋아요 상태를 조회하는 Repository 메서드
     * @param boardLikeId BoardLikeId
     * @return Boolean
     */
    Boolean existsByBoardLikeId(BoardLikeId boardLikeId);

    /**
     * boardId와 userId를 통해 해당 게시물의 좋아요 상태를 조회하는 Repository 메서드
     * @param boardLikeId BoardLikeId
     * @return Optional<BoardLike>
     */
    Optional<BoardLike> findByBoardLikeId(BoardLikeId boardLikeId);
}
