package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.exception.BookForbiddenException;
import com.dnd.sbooky.api.book.exception.BookNotFoundException;
import com.dnd.sbooky.api.book.exception.MemberNotFoundException;
import com.dnd.sbooky.api.book.response.FindAllBookResponse;
import com.dnd.sbooky.api.book.response.FindBookDetailsResponse;
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

    @Transactional(readOnly = true)
    public FindAllBookResponse findAllMemberBooks(
            Long currentMemberId, Long targetMemberId, ReadStatus readStatus) {

        MemberEntity member =
                memberRepository
                        .findById(targetMemberId)
                        .orElseThrow(() -> new MemberNotFoundException(ErrorType.MEMBER_NOT_FOUND));

        if (!member.isBookPublic() && !targetMemberId.equals(currentMemberId)) {
            throw new BookForbiddenException(ErrorType.FORBIDDEN);
        }

        return FindAllBookResponse.of(
                memberBookRepository.findMemberBookByMemberIdAndReadStatus(
                        targetMemberId, readStatus));
    }

    @Transactional(readOnly = true)
    public FindBookDetailsResponse findBookDetails(Long memberBookId) {

        MemberBookEntity memberBook =
                memberBookRepository
                        .findById(memberBookId)
                        .orElseThrow(() -> new BookNotFoundException(ErrorType.BOOK_NOT_FOUND));

        if (memberBook.isHidden()) {
            throw new BookForbiddenException(ErrorType.FORBIDDEN);
        }

        return FindBookDetailsResponse.of(memberBookRepository.findBookDetails(memberBookId));
    }
}
