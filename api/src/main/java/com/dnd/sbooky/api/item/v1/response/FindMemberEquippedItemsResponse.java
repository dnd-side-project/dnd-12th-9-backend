package com.dnd.sbooky.api.item.v1.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "내가 장착한 아이템 조회 응답")
public record FindMemberEquippedItemsResponse(
        @Schema(description = "아이템 타입에 따른 아이템 식별자 DTO")
                FindEquippedItemsResponse findEquippedItemsResponse,
        @Schema(description = "닉네임") String nickName) {
    public static FindMemberEquippedItemsResponse from(
            FindEquippedItemsResponse findEquippedItemsResponse, String nickName) {
        return new FindMemberEquippedItemsResponse(findEquippedItemsResponse, nickName);
    }
}
