package com.dnd.sbooky.api.item;

import static com.dnd.sbooky.api.support.error.ErrorType.*;

import com.dnd.sbooky.api.item.exception.MemberHasNotItemException;
import com.dnd.sbooky.core.item.ItemCode;
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

    public void switchEquippedItem(Long memberId, String equippedItemCode, String toEquipItemCode) {
        unEquipItem(memberId, ItemCode.toId(equippedItemCode));
        equipItem(memberId, ItemCode.toId(toEquipItemCode));
    }

    private void unEquipItem(Long memberId, Long equippedItemId) {
        MemberItemEntity equippedMemberItem =
                memberItemRepository
                        .findMemberItemByMemberIdAndItemId(memberId, equippedItemId)
                        .orElseThrow(() -> new MemberHasNotItemException(MEMBER_HAS_NOT_ITEM));
        equippedMemberItem.unEquip();
    }

    private void equipItem(Long memberId, Long toEquipItemId) {
        MemberItemEntity unEquippedMemberItem =
                memberItemRepository
                        .findMemberItemByMemberIdAndItemId(memberId, toEquipItemId)
                        .orElseThrow(() -> new MemberHasNotItemException(MEMBER_HAS_NOT_ITEM));
        unEquippedMemberItem.equip();
    }
}
