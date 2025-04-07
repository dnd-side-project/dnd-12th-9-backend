package com.dnd.sbooky.api.book.v2;

import com.dnd.sbooky.api.book.v1.exception.BookForbiddenException;
import com.dnd.sbooky.api.book.v1.exception.BookNotFoundException;
import com.dnd.sbooky.api.book.v2.response.FindAllBookResponseV2;
import com.dnd.sbooky.api.book.v2.response.FindBookCountResponseV2;
import com.dnd.sbooky.api.book.v2.response.FindBookDetailsResponseV2;
import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.book.MemberBookEntity;
import com.dnd.sbooky.core.book.MemberBookRepository;
import com.dnd.sbooky.core.book.ReadStatus;
import com.dnd.sbooky.core.book.dto.FindBookDTO;
import com.dnd.sbooky.core.member.MemberEntity;
import com.dnd.sbooky.core.member.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindBookUseCaseV2 {

    private final MemberBookRepository memberBookRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public FindAllBookResponseV2 findAllMemberBooks(
            Long visitorId, Long ownerId, ReadStatus readStatus) {

        MemberEntity owner = getMemberById(ownerId);
        validateBookshelfAccess(owner, visitorId);

        boolean isOwner = ownerId.equals(visitorId);

        List<FindBookDTO> memberBooks =
                memberBookRepository.findMemberBookByMemberIdAndReadStatus(ownerId, readStatus);
        return FindAllBookResponseV2.of(memberBooks, isOwner);
    }

    @Transactional(readOnly = true)
    public FindBookDetailsResponseV2 findBookDetails(Long memberBookId, Long visitorId) {

        MemberBookEntity memberBook = getMemberBookById(memberBookId);
        boolean isOwner = memberBook.isSameMember(visitorId);

        if (memberBook.isHidden() && !isOwner) {
            throw new BookForbiddenException(ErrorType.BOOK_ACCESS_FORBIDDEN);
        }

        return FindBookDetailsResponseV2.of(memberBook, isOwner);
    }

    @Transactional(readOnly = true)
    public FindBookCountResponseV2 findBookCountByReadStatus(
            Long ownerId, Long visitorId, ReadStatus readStatus) {
        MemberEntity member = getMemberById(ownerId);
        validateBookshelfAccess(member, visitorId);
        return FindBookCountResponseV2.from(memberBookRepository.countMemberBooks(ownerId, readStatus));
    }

    private MemberBookEntity getMemberBookById(Long memberBookId) {
        return memberBookRepository
                .findById(memberBookId)
                .orElseThrow(() -> new BookNotFoundException(ErrorType.BOOK_NOT_FOUND));
    }

    private MemberEntity getMemberById(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorType.MEMBER_NOT_FOUND));
    }

    private void validateBookshelfAccess(MemberEntity owner, Long visitorId) {
        if (!owner.isBookPublic() && !owner.getId().equals(visitorId)) {
            throw new BookForbiddenException(ErrorType.BOOK_ACCESS_FORBIDDEN);
        }
    }
}
