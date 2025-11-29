package com.example.boardserver.keyword.converter;

import com.example.boardserver.keyword.domain.Keyword;
import com.example.boardserver.keyword.dto.KeywordListResponseDTO;

import java.util.List;

public class KeywordConverter {

    /**
     * 검색한 단어를 Keyword Entity로 변환하는 메서드
     * @param keyword String
     * @return Keyword
     */
    public static Keyword toKeyword(String keyword) {
        return Keyword
                .builder()
                .keyword(keyword)
                .build();
    }

    /**
     * 조회한 검색어 리스트를 KeywordListResponseDTO 객체로 변환하는 메서드
     * @param keywords List<Keyword>
     * @return KeywordListResponseDTO
     */
    public static KeywordListResponseDTO toKeywordList(List<Keyword> keywords) {
        List<String> keywordList = keywords
                .stream()
                .map(Keyword::getKeyword)
                .toList();

        return KeywordListResponseDTO
                .builder()
                .keyWordList(keywordList)
                .build();
    }

    /**
     * 조회한 연관 키워드 리스트를 KeywordListResponseDTO 객체로 변환하는 메서드
     * @param keywords List<String>
     * @return KeywordListResponseDTO
     */
    public static KeywordListResponseDTO toRelatedKeywordList(List<String> keywords) {
        return KeywordListResponseDTO
                .builder()
                .keyWordList(keywords)
                .build();
    }
}
