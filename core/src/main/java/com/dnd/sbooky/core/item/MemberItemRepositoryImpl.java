package com.dnd.sbooky.core.item;

import com.dnd.sbooky.core.item.dto.FindItemDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberItemRepositoryImpl implements MemberItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QMemberItemEntity memberItem = QMemberItemEntity.memberItemEntity;
    private final QItemEntity item = QItemEntity.itemEntity;

    public List<FindItemDTO> findItemsByMemberId(Long memberId) {
        return queryFactory
                .select(
                        Projections.constructor(
                                FindItemDTO.class, memberItem.itemEntity.id, item.type))
                .from(memberItem)
                .join(memberItem.itemEntity, item)
                .where(memberItem.memberEntity.id.eq(memberId))
                .fetch();
    }

    @Override
    public List<FindItemDTO> findEquippedItemsByMemberId(Long memberId) {
        return queryFactory
                .select(
                        Projections.constructor(
                                FindItemDTO.class, memberItem.itemEntity.id, item.type))
                .from(memberItem)
                .join(memberItem.itemEntity, item)
                .where(memberItem.memberEntity.id.eq(memberId), isEquipped())
                .fetch();
    }

    private BooleanExpression isEquipped() {
        return memberItem.equipped.isTrue();
    }
}
