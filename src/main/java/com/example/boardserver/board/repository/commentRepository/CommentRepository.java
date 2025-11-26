package com.example.boardserver.board.repository.commentRepository;

import com.example.boardserver.board.domain.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    /**
     * BoardId를 통해 해당 게시물의 모든 댓글 목록을 조회하는 Repository 메서드
     * @param boardId Long
     * @return List<Comment>
     */
    List<Comment> findAllByBoard_BoardId(Long boardId);
}
