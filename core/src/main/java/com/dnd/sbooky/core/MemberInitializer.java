package com.dnd.sbooky.core;

import com.dnd.sbooky.core.like.LikeEntity;
import com.dnd.sbooky.core.like.LikeRepository;
import com.dnd.sbooky.core.member.MemberEntity;
import com.dnd.sbooky.core.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberInitializer {

    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;

    @PostConstruct
    public void init() {
        MemberEntity memberEntity = memberRepository.save(MemberEntity.newInstance("test1", "test1"));
        likeRepository.save(LikeEntity.newInstance(memberEntity.getId()));
    }
}
