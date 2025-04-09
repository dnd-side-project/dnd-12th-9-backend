package com.dnd.sbooky.api.book.v1.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CountCompletedBookResponse(
        @Schema(description = "완독한 책의 수") long completedBookCount) {

    public static CountCompletedBookResponse from(long completedBookCount) {
        return new CountCompletedBookResponse(completedBookCount);
    }
}
