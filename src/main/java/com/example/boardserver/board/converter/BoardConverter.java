package com.example.boardserver.board.converter;

import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.domain.BoardImg;
import com.example.boardserver.board.dto.BoardRequestDTO;
import com.example.boardserver.board.dto.BoardResponseDTO;
import com.example.boardserver.user.domain.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoardConverter {

    /**
     * BoardRequestDTO를 Board Entity로 변환하는 메소드
     * @param request BoardRequestDTO
     * @param user User
     * @return Board
     */
    public static Board toBoardEntity(BoardRequestDTO request, User user) {
        Board board = Board.builder()
                .user(user)
                .title(request.title())
                .content(request.content())
                .mainImg(request.boardMainImg())
                .build();

        if (request.boardImageList() != null) {
            List<BoardImg> boardImgList = Arrays.stream(request.boardImageList())
                    .map(img -> BoardImg.builder()
                            .board(board)
                            .img(img)
                            .build())
                    .toList();

            board.getBoardImgList().addAll(boardImgList);
        };

        return board;
    }

    /**
     * BoardRequestDTO를 기반으로 BoardImg  Entity 리스트 생성
     * @param request BoardRequestDTO
     * @param board Board
     * @return List<BoardImg>
     */
    public static List<BoardImg> toBoardImgEntityList(BoardRequestDTO request, Board board) {
        if (request.boardImageList() == null) return Collections.emptyList();

        return Arrays.stream(request.boardImageList())
                .map(img -> BoardImg.builder()
                        .board(board)
                        .img(img)
                        .build())
                .toList();
    }

    /**
     * Board Entity를 BoardResponseDTO로 변환하는 메서드
     * @param board Board
     * @return BoardResponseDTO
     */
    public static BoardResponseDTO toBoardResponseDTO(Board board) {
        return BoardResponseDTO.builder()
                .boardId(board.getBoardId())
                .userId(board.getUser().getUserId())
                .title(board.getTitle())
                .content(board.getContent())
                .mainImg(board.getMainImg())
                .boardImageList(board.getBoardImgList().stream()
                        .map(BoardImg::getImg)
                        .toArray(String[]::new))
                .likeCount(board.getLikeCount())
                .commentCount(board.getCommentCount())
                .viewCount(board.getViewCount())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();
    }
}
