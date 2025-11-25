package com.example.boardserver.board.controller;

import com.example.boardserver.auth.dto.CustomUserDetails;
import com.example.boardserver.board.converter.BoardConverter;
import com.example.boardserver.board.dto.BoardRequestDTO;
import com.example.boardserver.board.dto.BoardResponseDTO;
import com.example.boardserver.board.dto.BoardResponseDetailDTO;
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
}
