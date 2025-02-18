package com.dnd.sbooky.api.item.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "아이템 뽑기 응답 DTO")
public record DrawItemResponse(
        @Schema(description = "아이템 이름") String name,
        @Schema(description = "아이템 코드") String code,
        @Schema(description = "아이템 보유 시 메시지") String message) {

    public static final String ALREADY_OWNED_MESSAGE = "이미 보유 중입니다.";

    public static DrawItemResponse from(String name, String code, boolean isAlreadyOwned) {
        if (isAlreadyOwned) {
            return new DrawItemResponse(name, code, ALREADY_OWNED_MESSAGE);
        }
        return new DrawItemResponse(name, code, null);
    }
}
