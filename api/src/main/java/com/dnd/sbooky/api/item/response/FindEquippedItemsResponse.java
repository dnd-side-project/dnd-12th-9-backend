package com.dnd.sbooky.api.item.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "내가 장착한 아이템 조회 응답")
public record FindEquippedItemsResponse(
        @Schema(description = "아이템 타입에 따른 아이템 식별자 DTO") FindEquippedItemsDTO findEquippedItemsDTO,
        @Schema(description = "닉네임") String nickName) {
    public static FindEquippedItemsResponse from(
            FindEquippedItemsDTO findEquippedItemsDTO, String nickName) {
        return new FindEquippedItemsResponse(findEquippedItemsDTO, nickName);
    }
}
