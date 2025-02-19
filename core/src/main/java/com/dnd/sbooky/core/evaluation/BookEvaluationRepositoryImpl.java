package com.dnd.sbooky.core.evaluation;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookEvaluationRepositoryImpl implements BookEvaluationRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QBookEvaluationEntity bookEvaluation = QBookEvaluationEntity.bookEvaluationEntity;

    @Override
    public boolean existsByMemberBookId(Long memberBookId) {
        return queryFactory
                        .selectOne()
                        .from(bookEvaluation)
                        .where(bookEvaluation.memberBook.id.eq(memberBookId))
                        .fetchFirst()
                != null;
    }
}
