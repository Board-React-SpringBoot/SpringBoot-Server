package com.example.boardserver.keyword.controller;

import com.example.boardserver.common.ApiResponse;
import com.example.boardserver.keyword.dto.KeywordListResponseDTO;
import com.example.boardserver.keyword.service.KeywordQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/keyword")
@RequiredArgsConstructor
@Tag(name = "Keyword", description = "검색어 관련 API")
public class KeywordController {

    private final KeywordQueryService keywordQueryService;

    @GetMapping("/popular")
    public ApiResponse<KeywordListResponseDTO> getPopularKeywords() {
        return ApiResponse.onSuccess(keywordQueryService.getPopularKeywords());
    }
}
