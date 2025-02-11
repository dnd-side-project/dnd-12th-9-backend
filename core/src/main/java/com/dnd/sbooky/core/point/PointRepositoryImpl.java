package com.dnd.sbooky.core.point;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QPointEntity point = QPointEntity.pointEntity;

    @Override
    public int findCurrentPointByMemberId(Long memberId) {

        Integer result =
                queryFactory
                        .select(point.current)
                        .from(point)
                        .where(point.member.id.eq(memberId))
                        .orderBy(point.id.desc())
                        .limit(1)
                        .fetchOne();

        return result == null ? 0 : result;
    }
}
