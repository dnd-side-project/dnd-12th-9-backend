package com.dnd.sbooky.api.item;

import static com.dnd.sbooky.api.support.error.ErrorType.*;

import com.dnd.sbooky.api.item.exception.ItemNotFoundException;
import com.dnd.sbooky.api.member.exception.MemberNotFoundException;
import com.dnd.sbooky.core.item.ItemEntity;
import com.dnd.sbooky.core.item.ItemRepository;
import com.dnd.sbooky.core.item.MemberItemEntity;
import com.dnd.sbooky.core.item.MemberItemRepository;
import com.dnd.sbooky.core.member.MemberEntity;
import com.dnd.sbooky.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ObtainItemUseCase {

    private final MemberItemRepository memberItemRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    public void obtainItem(Long memberId, Long itemId) {
        ItemEntity itemEntity =
                itemRepository
                        .findById(itemId)
                        .orElseThrow(() -> new ItemNotFoundException(ITEM_NOT_FOUND));
        MemberEntity memberEntity =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        Boolean isExisted = memberItemRepository.existsByMemberIdAndItemId(memberId, itemId);
        if (!isExisted) {
            MemberItemEntity memberItemEntity = MemberItemEntity.obtainItem(memberEntity, itemEntity);
            memberItemRepository.save(memberItemEntity);
        }
    }
}
