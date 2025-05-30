package com.dnd.sbooky.api.member;

import static com.dnd.sbooky.api.support.error.ErrorType.MEMBER_NOT_FOUND;

import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.api.member.request.UpdateNicknameRequest;
import com.dnd.sbooky.core.member.MemberEntity;
import com.dnd.sbooky.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateNicknameUseCase {

    private final MemberRepository memberRepository;

    public void updateNickname(Long memberId, UpdateNicknameRequest request) {
        MemberEntity member =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        member.updateNickname(request.nickname());
    }
}
