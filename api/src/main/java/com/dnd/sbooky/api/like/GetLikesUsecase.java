package com.dnd.sbooky.api.like;

import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.like.LikeRepository;
import com.dnd.sbooky.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetLikesUsecase {
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Long get(Long memberId) {
        validateMember(memberId);
        return likeRepository.countByMemberEntityId(memberId);
    }

    private void validateMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException(ErrorType.MEMBER_NOT_FOUND);
        }
    }
}
