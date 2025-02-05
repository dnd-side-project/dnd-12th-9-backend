package com.dnd.sbooky.api.item.response;

import com.dnd.sbooky.core.item.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;

@Schema(description = "내가 보유한 아이템 조회 응답 DTO")
public record FindItemsResponse(
        @Schema(description = "아이템 타입에 따른 아이템 모음") Map<ItemType, List<FindItemResponse>> items) {
    public static FindItemsResponse from(Map<ItemType, List<FindItemResponse>> items) {
        return new FindItemsResponse(items);
    }
}
