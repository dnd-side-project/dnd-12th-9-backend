package com.dnd.sbooky.core.evaluation;

import com.dnd.sbooky.core.evaluation.dto.GetEvaluationDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EvaluationRepositoryImpl implements EvaluationRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QEvaluationEntity evaluation = QEvaluationEntity.evaluationEntity;
    private final QBookEvaluationEntity bookEvaluation = QBookEvaluationEntity.bookEvaluationEntity;

    @Override
    public List<GetEvaluationDTO> findAllWithSelectedByMemberBookId(Long memberBookId) {

        return queryFactory
                .select(
                        Projections.constructor(
                                GetEvaluationDTO.class,
                                evaluation.id,
                                evaluation.type,
                                evaluation.keyword,
                                bookEvaluation.isNotNull().as("isSelected")))
                .from(evaluation)
                .leftJoin(bookEvaluation)
                .on(
                        bookEvaluation
                                .evaluation
                                .id
                                .eq(evaluation.id)
                                .and(bookEvaluation.memberBook.id.eq(memberBookId)))
                .fetch();
    }
}
