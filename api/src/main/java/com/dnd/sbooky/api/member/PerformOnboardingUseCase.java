package com.dnd.sbooky.api.member;

import static com.dnd.sbooky.api.support.error.ErrorType.MEMBER_NOT_FOUND;

import com.dnd.sbooky.api.item.ObtainItemUseCase;
import com.dnd.sbooky.api.item.SwitchEquippedItemUsecase;
import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.api.member.request.PerformOnboardingRequest;
import com.dnd.sbooky.core.item.ItemCode;
import com.dnd.sbooky.core.member.MemberEntity;
import com.dnd.sbooky.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PerformOnboardingUseCase {
    private final MemberRepository memberRepository;
    private final ObtainItemUseCase obtainItemUseCase;
    private final SwitchEquippedItemUsecase switchEquippedItemUsecase;

    public void performOnboarding(Long memberId, PerformOnboardingRequest request) {
        MemberEntity member =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        member.updateNickname(request.nickname());
        obtainItemUseCase.obtainItem(memberId, ItemCode.BASIC.getId());
        switchEquippedItemUsecase.switchEquippedItem(
                memberId, ItemCode.MUMMY.getCode(), ItemCode.BASIC.getCode());
    }
}
