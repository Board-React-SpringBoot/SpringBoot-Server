package com.example.boardserver.board.converter;

import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.domain.Comment;
import com.example.boardserver.board.dto.CommentListResponseDTO;
import com.example.boardserver.board.dto.CommentRequestDTO;
import com.example.boardserver.board.dto.CommentResponseDTO;
import com.example.boardserver.user.domain.User;

import java.util.List;

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

    /**
     * 특정 게시물의 모든 Comment List를 CommentListResponseDTO로 변환하는 메서드
     * @param commentList List<Comment>
     * @return CommentResponseDTO.CommentListResponseDTO
     */
    public static CommentListResponseDTO toCommentListResponseDTO(List<Comment> commentList) {
        return CommentListResponseDTO.builder()
                .commentList(commentList.stream()
                        .map(CommentConverter::toCommentResponseDTO)
                        .toList()
                )
                .build();
    }
}
