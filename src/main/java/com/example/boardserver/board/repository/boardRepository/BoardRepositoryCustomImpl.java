package com.example.boardserver.board.repository.boardRepository;

import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.domain.QBoard;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QBoard board = QBoard.board;

    @Override
    public List<Board> findWeeklyTop3BoardList() {

        // 오늘 기준 7일 전
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);

        return queryFactory.selectFrom(board)
                .where(board.createdAt.goe(oneWeekAgo))
                .orderBy(
                        board.likeCount.desc(),
                        board.commentCount.desc(),
                        board.viewCount.desc(),
                        board.createdAt.desc()
                )
                .limit(3)
                .fetch();
    }

    @Override
    public Page<Board> searchBoardList(String keyword, Pageable pageable) {

        BooleanExpression condition = board.title.contains(keyword)
                .or(board.content.contains(keyword));

        List<Board> result = queryFactory
                .selectFrom(board)
                .where(condition)
                .orderBy(board.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = Optional.ofNullable(
                queryFactory
                        .select(board.count())
                        .from(board)
                        .where(board.title.contains(keyword))
                        .fetchFirst()
        ).orElse(0L);

        return new PageImpl<>(result, pageable, total);
    }
}
