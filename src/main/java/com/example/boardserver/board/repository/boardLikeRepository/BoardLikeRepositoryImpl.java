package com.example.boardserver.board.repository.boardLikeRepository;

import com.example.boardserver.board.domain.BoardLikeId;
import com.example.boardserver.board.domain.QBoard;
import com.example.boardserver.board.domain.QBoardLike;
import com.example.boardserver.board.dto.BoardLikeInfoDTO;
import com.example.boardserver.user.domain.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardLikeRepositoryImpl implements BoardLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QBoardLike boardLike = QBoardLike.boardLike ;
    private final QBoard board = QBoard.board;
    private final QUser user = QUser.user;

    @Override
    public Optional<BoardLikeInfoDTO> findBoardLike(BoardLikeId boardLikeId) {
        BoardLikeInfoDTO result = queryFactory
                .select(Projections.constructor(BoardLikeInfoDTO.class,
                        user.userId,
                        user.nickname,
                        board.boardId,
                        board.title))
                .from(boardLike)
                .join(boardLike.user, user)
                .join(boardLike.board, board)
                .where(
                       // boardLike.boardLikeId.userId.eq(boardLikeId.getUserId()),
                       // boardLike.boardLikeId.boardId.eq(boardLikeId.getBoardId())
                        boardLike.boardLikeId.eq(boardLikeId)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
