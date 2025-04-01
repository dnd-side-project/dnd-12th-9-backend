package com.dnd.sbooky.api.book.v1;

import com.dnd.sbooky.api.book.v1.exception.BookForbiddenException;
import com.dnd.sbooky.api.book.v1.exception.BookNotFoundException;
import com.dnd.sbooky.api.book.v1.response.FindAllBookResponse;
import com.dnd.sbooky.api.book.v1.response.FindBookDetailsResponse;
import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.book.MemberBookEntity;
import com.dnd.sbooky.core.book.MemberBookRepository;
import com.dnd.sbooky.core.book.ReadStatus;
import com.dnd.sbooky.core.member.MemberEntity;
import com.dnd.sbooky.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindBookUseCase {

    private final MemberBookRepository memberBookRepository;
    private final MemberRepository memberRepository;
    private final CountBookUseCase countBookUseCase;

    @Transactional(readOnly = true)
    public FindAllBookResponse findAllMemberBooks(
            Long visitorId, Long ownerId, ReadStatus readStatus) {

        MemberEntity member = validateMember(ownerId);
        validateBookAccess(member, ownerId, visitorId);

        return FindAllBookResponse.of(
                countBookUseCase.countTotalBooks(member),
                memberBookRepository.findMemberBookByMemberIdAndReadStatus(ownerId, readStatus));
    }

    @Transactional(readOnly = true)
    public FindBookDetailsResponse findBookDetails(Long memberBookId) {

        MemberBookEntity memberBook =
                memberBookRepository
                        .findById(memberBookId)
                        .orElseThrow(() -> new BookNotFoundException(ErrorType.BOOK_NOT_FOUND));

        if (memberBook.isHidden()) {
            throw new BookForbiddenException(ErrorType.BOOK_ACCESS_FORBIDDEN);
        }

        return FindBookDetailsResponse.of(memberBookRepository.findBookDetails(memberBookId));
    }

    private MemberEntity validateMember(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorType.MEMBER_NOT_FOUND));
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
            throw new BookForbiddenException(ErrorType.BOOK_ACCESS_FORBIDDEN);
        }
    }
}
