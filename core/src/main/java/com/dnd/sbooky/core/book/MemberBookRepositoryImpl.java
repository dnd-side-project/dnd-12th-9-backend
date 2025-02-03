package com.dnd.sbooky.core.book;

import com.dnd.sbooky.core.book.dto.FindBookDTO;
import com.dnd.sbooky.core.book.dto.FindBookDetailsDTO;
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
    public List<FindBookDTO> findMemberBookByMemberIdAndReadStatus(
            Long memberId, ReadStatus readStatus) {

        return queryFactory
                .select(
                        Projections.constructor(
                                FindBookDTO.class,
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

    @Override
    public FindBookDetailsDTO findBookDetails(Long memberBookId) {

        return queryFactory
                .select(
                        Projections.constructor(
                                FindBookDetailsDTO.class,
                                memberBook.id,
                                book.title,
                                book.author,
                                book.thumbnailUrl,
                                memberBook.readStatus,
                                book.publishedAt,
                                book.createdAt,
                                memberBook.completedAt))
                .from(memberBook)
                .join(memberBook.bookEntity, book)
                .where(memberBook.id.eq(memberBookId))
                .fetchOne();
    }

    private BooleanExpression readStatusEqual(ReadStatus readStatus) {
        return readStatus == null ? null : memberBook.readStatus.eq(readStatus);
    }
}
