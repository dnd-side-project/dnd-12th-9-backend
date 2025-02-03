package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.exception.BookNotFoundException;
import com.dnd.sbooky.api.book.exception.MemberNotFoundException;
import com.dnd.sbooky.api.book.response.FindAllBookResponse;
import com.dnd.sbooky.api.book.response.FindBookDetailsResponse;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.book.MemberBookRepository;
import com.dnd.sbooky.core.book.ReadStatus;
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
    public FindAllBookResponse findAllMemberBooks(Long memberId, ReadStatus readStatus) {

        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException(ErrorType.MEMBER_NOT_FOUND);
        }

        return FindAllBookResponse.of(
                memberBookRepository.findMemberBookByMemberIdAndReadStatus(memberId, readStatus));
    }

    @Transactional(readOnly = true)
    public FindBookDetailsResponse findBookDetails(Long memberBookId) {

        if (!memberBookRepository.existsById(memberBookId)) {
            throw new BookNotFoundException(ErrorType.BOOK_NOT_FOUND);
        }

        return FindBookDetailsResponse.of(memberBookRepository.findBookDetails(memberBookId));
    }
}
