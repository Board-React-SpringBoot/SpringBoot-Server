package com.example.boardserver.board.repository.commentRepository;

import com.example.boardserver.board.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;


public interface CommentRepository extends CrudRepository<Comment, Long> {

    /**
     * BoardId를 통해 해당 게시물의 댓글을 페이지로 분할하여 조회하는 Repository 메서드
     * @param boardId Long
     * @param pageable Pageable
     * @return Page<Comment>
     */
    @EntityGraph(attributePaths = {"user"})
    Page<Comment> findByBoard_BoardId(Long boardId, Pageable pageable);
}
