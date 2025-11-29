package com.example.boardserver.keyword.repository;

import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.domain.QBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class KeywordRepositoryCustomImpl implements KeywordRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QBoard board = QBoard.board;

    @Override
    public List<String> getRelatedKeywords(String keyword) {

        List<Board> boards = queryFactory
                .selectFrom(board)
                .where(board.title.contains(keyword)
                        .or(board.content.contains(keyword))
                )
                .fetch();

        Map<String, Integer> wordCount = new HashMap<>();

        for (Board b : boards) {
            String text = b.getTitle() + " " + b.getContent();
            String[] words = text.split("\\s+");

            for (String w : words) {
                if (w.equals(keyword)) continue;    // 검색한 키워드 제외
                if (w.length() < 2) continue;       // 의미 없는 키워드 제외
                wordCount.put(w, wordCount.getOrDefault(w, 0) + 1);
            }
        }

        return wordCount.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(10)
                .map(Map.Entry::getKey)
                .toList();
    }
}
