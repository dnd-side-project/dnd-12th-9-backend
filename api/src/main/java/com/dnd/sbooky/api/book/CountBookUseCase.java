package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.exception.BookForbiddenException;
import com.dnd.sbooky.api.book.response.CountCompletedBookResponse;
import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.api.support.error.ErrorType;
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
    public CountCompletedBookResponse countCompletedBooks(Long currentMemberId, Long targetMemberId) {
        MemberEntity member = validateMember(targetMemberId);
        validateBookAccess(member, targetMemberId, currentMemberId);

        return CountCompletedBookResponse.from(
                memberBookRepository.countMemberBooks(targetMemberId, ReadStatus.COMPLETED));
    }

    @Transactional(readOnly = true)
    public long countTotalBooks(MemberEntity member) {
        return memberBookRepository.countMemberBooks(member.getId(), null);
    }

    private MemberEntity validateMember(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorType.MEMBER_NOT_FOUND));
    }

    private void validateBookAccess(MemberEntity member, Long targetMemberId, Long currentMemberId) {
        if (!member.isBookPublic() && !targetMemberId.equals(currentMemberId)) {
            throw new BookForbiddenException(ErrorType.BOOK_ACCESS_FORBIDDEN);
        }
    }
}
