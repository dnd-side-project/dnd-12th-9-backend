package com.dnd.sbooky;

import com.dnd.sbooky.member.MemberEntity;
import com.dnd.sbooky.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberInitializer {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        memberRepository.save(MemberEntity.newInstance("test1", "test1"));
    }

}
