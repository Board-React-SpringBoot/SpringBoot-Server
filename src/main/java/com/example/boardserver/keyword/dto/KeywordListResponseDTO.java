package com.example.boardserver.keyword.dto;

import lombok.Builder;

import java.util.List;

/**
 * KeyWord 리스트를 조회한 Response DTO
 * @param keyWordList List<String>
 */
@Builder
public record KeywordListResponseDTO(
        List<String> keyWordList
) {
}
