package com.example.boardserver.board.dto.boardDTO;

import lombok.Builder;

import java.util.List;

/**
 * 게시물 리스트를 조회한 Response DTO
 * @param boardList List<BoardList>
 * @param listSize Integer
 * @param totalPage Integer
 * @param totalElements Long
 * @param isFirst Boolean
 * @param isLast Boolean
 */
@Builder
public record BoardListPageResponseDTO(
        List<BoardList> boardList,
        Integer listSize,
        Integer totalPage,
        Long totalElements,
        Boolean isFirst,
        Boolean isLast
) {
}
