package com.dnd.sbooky.core;

import com.dnd.sbooky.core.evaluation.EvaluationEntity;
import com.dnd.sbooky.core.evaluation.EvaluationKeyword;
import com.dnd.sbooky.core.evaluation.EvaluationRepository;
import com.dnd.sbooky.core.item.ItemEntity;
import com.dnd.sbooky.core.item.ItemRepository;
import com.dnd.sbooky.core.item.ItemType;
import com.dnd.sbooky.core.like.LikeEntity;
import com.dnd.sbooky.core.like.LikeRepository;
import com.dnd.sbooky.core.member.MemberEntity;
import com.dnd.sbooky.core.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final ItemRepository itemRepository;
    private final EvaluationRepository evaluationRepository;

    @PostConstruct
    public void init() {
        itemRepository.save(ItemEntity.newInstance(1L, ItemType.CHARACTER, "떠돌이 유령"));
        itemRepository.save(ItemEntity.newInstance(2L, ItemType.CHARACTER, "유령"));
        itemRepository.save(ItemEntity.newInstance(3L, ItemType.CHARACTER, "고양이 유령"));
        itemRepository.save(ItemEntity.newInstance(4L, ItemType.CHARACTER, "마법사 유령"));
        itemRepository.save(ItemEntity.newInstance(5L, ItemType.CHARACTER, "명탐정 유령"));
        itemRepository.save(ItemEntity.newInstance(6L, ItemType.CHARACTER, "어린왕자 유령"));
        itemRepository.save(ItemEntity.newInstance(7L, ItemType.CHARACTER, "프랑켄슈타인 유령"));
        itemRepository.save(ItemEntity.newInstance(8L, ItemType.CHARACTER, "빨간망토 유령"));
        itemRepository.save(ItemEntity.newInstance(9L, ItemType.CHARACTER, "치즈 고양이 유령"));
        itemRepository.save(ItemEntity.newInstance(10L, ItemType.CHARACTER, "샴 고양이 유령"));
        itemRepository.save(ItemEntity.newInstance(11L, ItemType.CHARACTER, "백도 고양이 유령"));

        Arrays.stream(EvaluationKeyword.values())
                .forEach(keyword -> evaluationRepository.save(EvaluationEntity.newInstance(keyword)));
    }
}
