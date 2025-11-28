package com.example.boardserver.keyword.service;

import com.example.boardserver.keyword.converter.KeywordConverter;
import com.example.boardserver.keyword.dto.KeywordListResponseDTO;
import com.example.boardserver.keyword.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordQueryServiceImpl implements KeywordQueryService {

    private final KeywordRepository keywordRepository;

    @Override
    public KeywordListResponseDTO getPopularKeywords() {
        return KeywordConverter.toKeywordList(keywordRepository.findAllByOrderByCountDesc());
    }
}
