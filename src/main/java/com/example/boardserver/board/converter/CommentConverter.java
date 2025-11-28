package com.example.boardserver.board.converter;

import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.domain.Comment;
import com.example.boardserver.board.dto.commentDTO.CommentListResponseDTO;
import com.example.boardserver.board.dto.commentDTO.CommentRequestDTO;
import com.example.boardserver.board.dto.commentDTO.CommentResponseDTO;
import com.example.boardserver.user.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

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
                .profile(
                        Optional.ofNullable(comment.getUser().getProfile())
                                .filter(s -> !s.isEmpty())
                                .orElse(null)
                )
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
    public static CommentListResponseDTO toCommentListResponseDTO(Page<Comment> commentList) {

        List<CommentResponseDTO> commentResponseDTOList = commentList
                .stream()
                .map(CommentConverter::toCommentResponseDTO)
                .toList();

        return CommentListResponseDTO.builder()
                .commentList(commentResponseDTOList)
                .listSize(commentResponseDTOList.size())
                .totalPage(commentList.getTotalPages())
                .totalElements(commentList.getTotalElements())
                .isFirst(commentList.isFirst())
                .isLast(commentList.isLast())
                .build();
    }
}
