package com.example.boardserver.board.converter;

import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.domain.BoardLike;
import com.example.boardserver.board.domain.BoardLikeId;
import com.example.boardserver.user.domain.User;

public class BoardLikeConverter {

    /**
     * 특정 게시물의 좋아요를 추가하는 BoardLike 엔티티 생성 메서드
     * @param user User
     * @param board Board
     * @return BoardLike
     */
    public static BoardLike toBoardLike(User user, Board board) {
        return BoardLike.builder()
                .boardLikeId(new BoardLikeId(user.getUserId(), board.getBoardId()))
                .user(user)
                .board(board)
                .build();
    }
}
