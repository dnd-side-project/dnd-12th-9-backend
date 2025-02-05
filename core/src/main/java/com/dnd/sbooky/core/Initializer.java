package com.dnd.sbooky.core;

import com.dnd.sbooky.core.item.ItemEntity;
import com.dnd.sbooky.core.item.ItemRepository;
import com.dnd.sbooky.core.item.ItemType;
import com.dnd.sbooky.core.like.LikeEntity;
import com.dnd.sbooky.core.like.LikeRepository;
import com.dnd.sbooky.core.member.MemberEntity;
import com.dnd.sbooky.core.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final ItemRepository itemRepository;

    @PostConstruct
    public void init() {
        MemberEntity memberEntity =
                memberRepository.save(MemberEntity.newInstance("test1", "test1"));
        likeRepository.save(LikeEntity.newInstance(memberEntity.getId()));
        itemRepository.save(ItemEntity.newInstance(ItemType.CHARACTER));
        itemRepository.save(ItemEntity.newInstance(ItemType.CHARACTER));
    }
}
