package com.example.boardserver.board.controller;

import com.example.boardserver.auth.dto.CustomUserDetails;
import com.example.boardserver.board.converter.BoardConverter;
import com.example.boardserver.board.domain.BoardLikeId;
import com.example.boardserver.board.dto.*;
import com.example.boardserver.board.service.commentService.CommentCommandService;
import com.example.boardserver.board.service.commentService.CommentQueryService;
import com.example.boardserver.board.service.boardLikeService.BoardLikeCommandService;
import com.example.boardserver.board.service.boardLikeService.BoardLikeQueryService;
import com.example.boardserver.board.service.boardService.BoardCommandService;
import com.example.boardserver.board.service.boardService.BoardQueryService;
import com.example.boardserver.common.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
@Tag(name = "Board", description = "게시물 관련 API")
public class BoardController {

    private final BoardCommandService boardCommandService;
    private final BoardQueryService boardQueryService;
    private final BoardLikeQueryService boardLikeQueryService;
    private final BoardLikeCommandService boardLikeCommandService;
    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;

    @PostMapping("")
    public ApiResponse<BoardResponseDTO> postBoard(
            @RequestBody @Valid BoardRequestDTO request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ApiResponse.onSuccess(
                BoardConverter.toBoardResponseDTO(
                        boardCommandService.saveBoard(request, user.userId())
                )
        );
    }

    @GetMapping("/{boardId}")
    public ApiResponse<BoardResponseDetailDTO> getBoardDetail(
            @PathVariable("boardId") Long boardId
    ) {
        return ApiResponse.onSuccess(boardQueryService.getBoardDetail(boardId));
    }

    @GetMapping("/{boardId}/like")
    public ApiResponse<Boolean> getBoardLike(
            @PathVariable("boardId") Long boardId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ApiResponse.onSuccess(boardLikeQueryService.findBoardLike(user.userId(), boardId));
    }

    @PutMapping("/{boardId}/like")
    public ApiResponse<BoardResponseDetailDTO> putBoardLike(
            @PathVariable("boardId") Long boardId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ApiResponse.onSuccess(
                boardLikeCommandService.toggleBoardLike(
                        new BoardLikeId(user.userId(), boardId
                        )
                )
        );
    }

    @PostMapping("/{boardId}/comment")
    public ApiResponse<CommentResponseDTO> postComment(
            @PathVariable("boardId") Long boardId,
            @RequestBody @Valid CommentRequestDTO request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ApiResponse.onSuccess(
                commentCommandService.saveComment(request, user.userId(), boardId)
        );
    }

    @GetMapping("/{boardId}/comment")
    public ApiResponse<CommentListResponseDTO> getCommentList(
            @PathVariable("boardId") Long boardId
    ) {
        return ApiResponse.onSuccess(commentQueryService.getCommentsList(boardId));
    }
}
