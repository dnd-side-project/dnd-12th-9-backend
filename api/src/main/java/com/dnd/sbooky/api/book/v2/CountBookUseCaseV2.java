package com.dnd.sbooky.api.book.v2;

import static com.dnd.sbooky.api.support.error.ErrorType.MEMBER_NOT_FOUND;

import com.dnd.sbooky.api.book.v2.response.CountBookResponse;
import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.core.book.MemberBookRepository;
import com.dnd.sbooky.core.book.ReadStatus;
import com.dnd.sbooky.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CountBookUseCaseV2 {

    private final MemberBookRepository memberBookRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public CountBookResponse count(Long ownerId, ReadStatus readStatus) {
        validateMember(ownerId);
        return CountBookResponse.from(memberBookRepository.countMemberBooks(ownerId, readStatus));
    }

    private void validateMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException(MEMBER_NOT_FOUND);
        }
    }
}
