package com.example.boardserver.keyword.service;

import com.example.boardserver.keyword.dto.KeywordListResponseDTO;

public interface KeywordQueryService {

    /**
     * 인기 검색어 목록을 조회하는 Service 메서드
     * @return KeywordListResponseDTO
     */
    KeywordListResponseDTO getPopularKeywords();

    /**
     * 검색어와 연관된 키워드 리스트를 조회하는 Service 메서드
     * @return KeywordListResponseDTO
     */
    KeywordListResponseDTO getRelatedKeywords(String keyword);
}
