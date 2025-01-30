package com.dnd.sbooky.core.book;

import com.dnd.sbooky.core.book.dto.MemberBookResponseDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MemberBookRepositoryImpl implements MemberBookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private QMemberBookEntity memberBookEntity = QMemberBookEntity.memberBookEntity;
    private QBookEntity bookEntity = QBookEntity.bookEntity;

    @Override
    public List<MemberBookResponseDTO> findMemberBookByMemberIdAndReadStatus(Long memberId, ReadStatus readStatus) {

        return queryFactory
                .select(Projections.constructor(MemberBookResponseDTO.class,
                        memberBookEntity.id,
                        bookEntity.title,
                        bookEntity.author,
                        bookEntity.thumbnailUrl,
                        memberBookEntity.readStatus))
                .from(memberBookEntity)
                .join(memberBookEntity.bookEntity, bookEntity)
                .where(
                        memberBookEntity.memberEntity.id.eq(memberId),
                        readStatus == null ? null : memberBookEntity.readStatus.eq(readStatus)
                )
                .orderBy(memberBookEntity.id.desc())
                .fetch();
    }
}
