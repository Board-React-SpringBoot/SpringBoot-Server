package com.example.boardserver.board.repository.boardRepository;

import com.example.boardserver.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer>, BoardRepositoryCustom {

    /**
     * BoardId로 DB에서 해당 게시물 Entity를 조회하는 Repository 메서드
     * @param boardId Long
     * @return Optional<Board>
     */
    Optional<Board> findByBoardId(Long boardId);

    /**
     * 모든 게시물 Entity 리스트를 조회하는 Repository 메서드
     * @param pageable Pageable
     * @return Page<Board>
     */
    Page<Board> findAll(Pageable pageable);

    /**
     * 해당 검색어를 포함한 제목을 가진 게시물 리스트 조회하는 Repository 메서드
     * @param title String
     * @param pageable Pageable
     * @return Page<Board>
     */
    Page<Board> findByTitleContaining(String title, Pageable pageable);

    /**
     * UserId를 통해 특정 유저가 업로드한 모든 게시물 리스트를 조회하는 Repository 메서드
     * @param userId Long
     * @param pageable Pageable
     * @return Page<Board>
     */
    Page<Board> findAllByUser_UserId(Long userId, Pageable pageable);
}
