package com.example.boardserver.keyword.repository;

import java.util.List;

public interface KeywordRepositoryCustom  {

    /**
     * 해당 검색어와 연관된 검색어 목록을 조회하는 Repository 메서드
     * @param keyword String
     * @return List<String>
     */
    List<String> getRelatedKeywords(String keyword);
}
