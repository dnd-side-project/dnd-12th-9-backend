package com.dnd.sbooky.api.item.response;

import com.dnd.sbooky.core.item.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;

@Schema(description = "아이템 타입에 따른 아이템 식별자")
public record FindEquippedItemsDTO(
        @Schema(description = "아이템 타입에 따른 아이템 code") Map<ItemType, List<String>> items) {
    public static FindEquippedItemsDTO from(Map<ItemType, List<String>> equippedItems) {
        return new FindEquippedItemsDTO(equippedItems);
    }
}
