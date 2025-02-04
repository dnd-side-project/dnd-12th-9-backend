package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.response.CheckBookResponse;
import com.dnd.sbooky.core.book.MemberBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckBookUseCase {

    private final MemberBookRepository memberBookRepository;

    @Transactional(readOnly = true)
    public CheckBookResponse checking(Long memberId, String title, String author) {

        return new CheckBookResponse(memberBookRepository.checkBookExist(memberId, title, author));
    }
}
