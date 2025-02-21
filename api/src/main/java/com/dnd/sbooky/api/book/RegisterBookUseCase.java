package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.request.RegisterBookRequest;
import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.api.point.AccumulatePointUseCase;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.book.BookEntity;
import com.dnd.sbooky.core.book.BookRepository;
import com.dnd.sbooky.core.book.MemberBookEntity;
import com.dnd.sbooky.core.book.MemberBookRepository;
import com.dnd.sbooky.core.book.ReadStatus;
import com.dnd.sbooky.core.member.MemberEntity;
import com.dnd.sbooky.core.member.MemberRepository;
import com.dnd.sbooky.core.point.PointPolicy;
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
    private final AccumulatePointUseCase accumulatePointUseCase;

    public void registerBook(RegisterBookRequest request, Long memberId) {
        MemberEntity member =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new MemberNotFoundException(ErrorType.MEMBER_NOT_FOUND));

        // fixme: 문제 발생 지점.. 처음에 등록한 책 정보를 기반으로 썸네일이 없을 수도 있음..
        BookEntity book =
                bookRepository
                        .findByAuthorAndTitleAndThumbnailUrl(
                                request.author(), request.title(), request.thumbnailUrl())
                        .orElseGet(() -> bookRepository.save(createBook(request)));

        memberBookRepository.save(
                createMemberBook(member, book, ReadStatus.valueOf(request.readStatus())));

        accumulatePointUseCase.accumulate(member, PointPolicy.REGISTER_BOOK);
    }

    private BookEntity createBook(RegisterBookRequest request) {
        return BookEntity.newInstance(
                request.author(), request.title(), request.publishedAt(), request.thumbnailUrl());
    }

    private MemberBookEntity createMemberBook(
            MemberEntity member, BookEntity book, ReadStatus readStatus) {
        return MemberBookEntity.newInstance(member, book, readStatus);
    }
}
