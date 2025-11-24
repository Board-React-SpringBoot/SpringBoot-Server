package com.example.boardserver.board.controller;

import com.example.boardserver.auth.dto.CustomUserDetails;
import com.example.boardserver.board.converter.BoardConverter;
import com.example.boardserver.board.dto.BoardRequestDTO;
import com.example.boardserver.board.dto.BoardResponseDTO;
import com.example.boardserver.board.service.boardService.BoardCommandService;
import com.example.boardserver.common.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
@Tag(name = "Board", description = "게시물 관련 API")
public class BoardController {

    private final BoardCommandService boardCommandService;

    @PostMapping("")
    public ApiResponse<BoardResponseDTO> postBoard(@RequestBody BoardRequestDTO request) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ApiResponse.onSuccess(BoardConverter.toBoardResponseDTO(boardCommandService.saveBoard(request, user.userId())));
    }
}
