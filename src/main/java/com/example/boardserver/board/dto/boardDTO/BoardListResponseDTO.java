package com.example.boardserver.board.dto.boardDTO;

import lombok.Builder;

import java.util.List;

/**
 * 주간 Top3 게시물 리스트 Response DTO
 * @param boardList List<BoardList>
 */
@Builder
public record BoardListResponseDTO(
        List<BoardList> boardList
) {
}
