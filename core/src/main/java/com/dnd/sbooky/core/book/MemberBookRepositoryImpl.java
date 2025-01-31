package com.dnd.sbooky.core.book;

import com.dnd.sbooky.core.book.dto.MemberBookResponseDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberBookRepositoryImpl implements MemberBookRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QMemberBookEntity memberBook = QMemberBookEntity.memberBookEntity;
    private final QBookEntity book = QBookEntity.bookEntity;

    @Override
    public List<MemberBookResponseDTO> findMemberBookByMemberIdAndReadStatus(
            Long memberId, ReadStatus readStatus) {

        return queryFactory
                .select(
                        Projections.constructor(
                                MemberBookResponseDTO.class,
                                memberBook.id,
                                book.title,
                                book.author,
                                book.thumbnailUrl,
                                memberBook.readStatus))
                .from(memberBook)
                .join(memberBook.bookEntity, book)
                .where(memberBook.memberEntity.id.eq(memberId), readStatusEqual(readStatus))
                .orderBy(memberBook.id.desc())
                .fetch();
    }

    private BooleanExpression readStatusEqual(ReadStatus readStatus) {
        return readStatus == null ? null : memberBook.readStatus.eq(readStatus);
    }
}
