package com.example.boardserver.board.repository.boardRepository;

import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.domain.QBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
}
