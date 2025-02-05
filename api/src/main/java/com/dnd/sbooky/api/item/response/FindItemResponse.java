package com.dnd.sbooky.api.item.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record FindItemResponse(
        @Schema(description = "아이템 이름")String name,
        @Schema(description = "아이템 식별자") String code) {
    public static FindItemResponse from(String name, String code) {
        return new FindItemResponse(name, code);
    }
}
