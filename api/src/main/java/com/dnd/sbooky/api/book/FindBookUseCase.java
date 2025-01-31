package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.response.FindAllBookResponse;
import com.dnd.sbooky.core.book.MemberBookRepository;
import com.dnd.sbooky.core.book.ReadStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindBookUseCase {

    private final MemberBookRepository memberBookRepository;

    @Transactional(readOnly = true)
    public FindAllBookResponse findAllMemberBooks(Long memberId, ReadStatus readStatus) {

        return FindAllBookResponse
                .of(memberBookRepository.findMemberBookByMemberIdAndReadStatus(memberId, readStatus));
    }
}
