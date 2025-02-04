package com.dnd.sbooky.api.like;

import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.like.LikeEntity;
import com.dnd.sbooky.core.like.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetLikesUsecase {
    private final LikeRepository likeRepository;

    @Transactional(readOnly = true)
    public Long get(Long memberId) {
        LikeEntity likeEntity =
                likeRepository
                        .findById(memberId)
                        .orElseThrow(() -> new MemberNotFoundException(ErrorType.MEMBER_NOT_FOUND));
        return likeEntity.getCount();
    }
}
