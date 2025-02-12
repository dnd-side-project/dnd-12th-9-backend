package com.dnd.sbooky.api.item;

import static com.dnd.sbooky.api.support.error.ErrorType.*;

import com.dnd.sbooky.api.item.exception.MemberHasNotItemException;
import com.dnd.sbooky.api.item.request.SwitchEquippedItemRequest;
import com.dnd.sbooky.core.item.MemberItemEntity;
import com.dnd.sbooky.core.item.MemberItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SwitchEquippedItemUsecase {

    private final MemberItemRepository memberItemRepository;

    public void switchEquippedItem(Long memberId, SwitchEquippedItemRequest request) {
        MemberItemEntity equippedMemberItem =
                memberItemRepository
                        .findMemberItemByMemberIdAndItemId(memberId, request.equippedItemId())
                        .orElseThrow(() -> new MemberHasNotItemException(MEMBER_HAS_NOT_ITEM));
        equippedMemberItem.unEquip();
        MemberItemEntity unEquippedMemberItem =
                memberItemRepository
                        .findMemberItemByMemberIdAndItemId(memberId, request.toEquipItemId())
                        .orElseThrow(() -> new MemberHasNotItemException(MEMBER_HAS_NOT_ITEM));
        unEquippedMemberItem.equip();
    }
}
