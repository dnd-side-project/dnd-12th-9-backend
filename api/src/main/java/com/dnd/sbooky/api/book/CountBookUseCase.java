package com.dnd.sbooky.api.book;

import static com.dnd.sbooky.api.support.error.ErrorType.BOOK_ACCESS_FORBIDDEN;
import static com.dnd.sbooky.api.support.error.ErrorType.MEMBER_NOT_FOUND;

import com.dnd.sbooky.api.book.exception.BookForbiddenException;
import com.dnd.sbooky.api.book.response.CountCompletedBookResponse;
import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.core.book.MemberBookRepository;
import com.dnd.sbooky.core.book.ReadStatus;
import com.dnd.sbooky.core.member.MemberEntity;
import com.dnd.sbooky.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CountBookUseCase {

    private final MemberBookRepository memberBookRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public CountCompletedBookResponse countCompletedBooks(Long visitorId, Long ownerId) {
        MemberEntity member = validateMember(ownerId);
        validateBookAccess(member, ownerId, visitorId);

        return CountCompletedBookResponse.from(
                memberBookRepository.countMemberBooks(ownerId, ReadStatus.COMPLETED));
    }

    @Transactional(readOnly = true)
    public long countTotalBooks(MemberEntity member) {
        return memberBookRepository.countMemberBooks(member.getId(), null);
    }

    private MemberEntity validateMember(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
    }

    /**
     * 책장 공개 여부 및 방문자와 주인이 같은지 검증한다.
     *
     * @param member    책장 주인
     * @param ownerId   책장 주인 ID
     * @param visitorId 방문자 ID
     */
    private void validateBookAccess(MemberEntity member, Long ownerId, Long visitorId) {
        if (!member.isBookPublic() && !ownerId.equals(visitorId)) {
            throw new BookForbiddenException(BOOK_ACCESS_FORBIDDEN);
        }
    }
}
