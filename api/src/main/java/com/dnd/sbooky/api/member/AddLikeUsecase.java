package com.dnd.sbooky.api.member;

import com.dnd.sbooky.api.book.exception.MemberNotFoundException;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.like.LikeEntity;
import com.dnd.sbooky.core.like.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddLikeUsecase {

    private final LikeRepository likeRepository;

    public Long add(Long memberId, Long addCount) {
        LikeEntity likeEntity =
                likeRepository
                        .findById(memberId)
                        .orElseThrow(() -> new MemberNotFoundException(ErrorType.MEMBER_NOT_FOUND));
        return likeEntity.add(addCount);
    }
}
