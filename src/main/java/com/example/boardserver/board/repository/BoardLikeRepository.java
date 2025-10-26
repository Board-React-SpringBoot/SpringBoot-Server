package com.example.boardserver.board.repository;

import com.example.boardserver.board.domain.BoardLike;
import com.example.boardserver.board.domain.BoardLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikeId> {

    /**
     * JPA 방식으로 User 엔티티의 userId를 조회 + Board 엔티티의 boardId를 조회
     * @param userId 유저 번호
     * @param boardId 게시물 번호
     * @return Optional<BoardLike>
     */
    Optional<BoardLike> findByUser_UserIdAndBoard_BoardId(Long userId, Long boardId);

    /**
     * JPQL 방식으로 userId와 boardId를 사용하여 조회
     * @param userId 유저 번호
     * @param boardId 게시물 번호
     * @return Optional<BoardLike>
     */
    @Query("SELECT bl FROM BoardLike bl WHERE bl.user.userId = :userId AND bl.board.boardId = :boardId")
    Optional<BoardLike> findByUserIdAndBoardId(@Param("userId") Long userId, @Param("boardId") Long boardId);

    /**
     * 복합키 객체를 생성하여 조회
     * @param boardLikeId BoardLikeId
     * @return Optional<BoardLike>
     */
    Optional<BoardLike> findByBoardLikeId(BoardLikeId boardLikeId);
}
