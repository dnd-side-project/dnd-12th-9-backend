package com.dnd.sbooky.api.item.response;

import com.dnd.sbooky.core.item.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;

@Schema(description = "내가 장착한 아이템 조회 응답 DTO")
public record FindEquippedItemsResponse(
        @Schema(description = "아이템 타입에 따른 아이템 식별자 모음") Map<ItemType, List<String>> items
) {
    public static FindEquippedItemsResponse from(Map<ItemType, List<String>> equippedItems) {
        return new FindEquippedItemsResponse(equippedItems);
    }
}
