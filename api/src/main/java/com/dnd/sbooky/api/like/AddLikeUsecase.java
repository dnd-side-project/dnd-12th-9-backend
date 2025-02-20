package com.dnd.sbooky.api.like;

import static com.dnd.sbooky.api.support.error.ErrorType.*;

import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.core.like.LikeEntity;
import com.dnd.sbooky.core.like.LikeRepository;
import com.dnd.sbooky.core.member.MemberEntity;
import com.dnd.sbooky.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddLikeUsecase {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;

    public void add(Long memberId, Long addCount) {
        MemberEntity memberEntity =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        likeRepository.saveAll(LikeEntity.newInstances(memberEntity, addCount));
    }
}
