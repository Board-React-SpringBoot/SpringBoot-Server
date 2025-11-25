package com.example.boardserver.board.converter;

import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.domain.Comment;
import com.example.boardserver.board.dto.CommentRequestDTO;
import com.example.boardserver.board.dto.CommentResponseDTO;
import com.example.boardserver.user.domain.User;

public class CommentConverter {

    /**
     * CommentRequestDTO를 Comment Entity로 변환하는 메서드
     * @param request CommentRequestDTO
     * @param board Board
     * @param user User
     * @return Comment
     */
    public static Comment toComment(CommentRequestDTO request, Board board, User user) {
        return Comment.builder()
                .user(user)
                .board(board)
                .content(request.content())
                .build();
    }

    /**
     * Comment Entity를 CommentResponseDTO로 변환하는 메서드
     * @param comment Comment
     * @return CommentResponseDTO
     */
    public static CommentResponseDTO toCommentResponseDTO(Comment comment) {
        return CommentResponseDTO.builder()
                .commentId(comment.getCommentId())
                .userId(comment.getUser().getUserId())
                .nickname(comment.getUser().getNickname())
                .profile(comment.getUser().getProfile().isEmpty() ? null : comment.getUser().getProfile())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
