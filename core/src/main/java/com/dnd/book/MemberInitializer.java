package com.dnd.book;

import com.dnd.book.member.MemberEntity;
import com.dnd.book.member.MemberRepository;
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
