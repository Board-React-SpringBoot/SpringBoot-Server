package com.example.boardserver.board.dto;

import lombok.Builder;

@Builder
public record BoardLikeInfoDTO(
        Long userId,
        String nickname,
        Long boardId,
        String title
) {
}
