package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.exception.BookForbiddenException;
import com.dnd.sbooky.api.book.request.UpdateBookRequest;
import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.api.point.AccumulatePointUseCase;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.book.BookEntity;
import com.dnd.sbooky.core.book.MemberBookEntity;
import com.dnd.sbooky.core.book.MemberBookRepository;
import com.dnd.sbooky.core.book.ReadStatus;
import com.dnd.sbooky.core.point.PointPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UpdateBookUseCase {

    private final MemberBookRepository memberBookRepository;
    private final AccumulatePointUseCase accumulatePointUseCase;

    public void update(Long memberId, Long memberBookId, UpdateBookRequest request) {

        MemberBookEntity memberBookEntity =
                memberBookRepository
                        .findById(memberBookId)
                        .orElseThrow(() -> new MemberNotFoundException(ErrorType.MEMBER_NOT_FOUND));

        validateMemberAccess(memberId, memberBookEntity);

        ReadStatus readStatus = ReadStatus.valueOf(request.readStatus());
        memberBookEntity.updateStatus(readStatus);

        BookEntity bookEntity = memberBookEntity.getBookEntity();
        bookEntity.update(request.author(), request.title(), request.publishedAt());

        if (readStatus == ReadStatus.COMPLETED) {
            // todo : 책 정보가 COMPLETE 된 시점에 1번만 지급해야 하는게 맞지 않나?
            accumulatePointUseCase.accumulate(
                    memberBookEntity.getMemberEntity(), PointPolicy.COMPLETE_BOOK);
        }
    }

    private void validateMemberAccess(Long memberId, MemberBookEntity memberBook) {
        if (!memberBook.isSameMember(memberId))
            throw new BookForbiddenException(ErrorType.BOOK_ACCESS_FORBIDDEN);
    }
}
