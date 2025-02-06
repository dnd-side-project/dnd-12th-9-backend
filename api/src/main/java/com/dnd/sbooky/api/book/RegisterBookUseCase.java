package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.request.RegisterBookRequest;
import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.book.BookEntity;
import com.dnd.sbooky.core.book.BookRepository;
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
@Transactional
public class RegisterBookUseCase {
    private final MemberBookRepository memberBookRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public void registerBook(RegisterBookRequest request, Long memberId) {
        MemberEntity member =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new MemberNotFoundException(ErrorType.MEMBER_NOT_FOUND));

        BookEntity book =
                bookRepository
                        .findByAuthorAndTitle(request.author(), request.title())
                        .orElseGet(() -> bookRepository.save(createBook(request)));

        memberBookRepository.save(
                createMemberBook(member, book, ReadStatus.valueOf(request.readStatus())));
    }

    private BookEntity createBook(RegisterBookRequest request) {
        return BookEntity.newInstance(request.author(), request.title(), request.publishedAt());
    }

    private MemberBookEntity createMemberBook(
            MemberEntity member, BookEntity book, ReadStatus readStatus) {
        return MemberBookEntity.newInstance(member, book, readStatus);
    }
}
