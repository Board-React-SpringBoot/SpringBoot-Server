package com.example.boardserver.keyword.repository;

import com.example.boardserver.keyword.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Integer>, KeywordRepositoryCustom {

    /**
     * 키워드 List를 검색된 횟수 순으로 정렬하여 조회하는 Repository 메서드
     * @return List<Keyword>
     */
    List<Keyword> findAllByOrderByCountDesc();

    /**
     * 해당 키워드가 존재하는지 조회하는 Repository 메서드
     * @param keyword String
     * @return Optional<Keyword>
     */
    Optional<Keyword> findByKeyword(String keyword);
}
