package com.dnd.sbooky.api.member;

import static com.dnd.sbooky.api.support.error.ErrorType.*;

import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.core.member.MemberEntity;
import com.dnd.sbooky.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindNicknameUsecase {

    private final MemberRepository memberRepository;
    @Transactional(readOnly = true)
    public String findNickname(Long memberId){
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        return memberEntity.getNickname();
    }
}
