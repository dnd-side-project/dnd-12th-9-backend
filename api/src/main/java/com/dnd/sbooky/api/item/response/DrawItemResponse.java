package com.dnd.sbooky.api.item.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "아이템 뽑기 응답 DTO")
public record DrawItemResponse(
        @Schema(description = "아이템 이름") String name,
        @Schema(description = "아이템 코드") String code,
        @Schema(description = "아이템 보유 시 메시지") String message) {
    public static DrawItemResponse from(String name, String code, String message) {
        return new DrawItemResponse(name, code, message);
    }
}
