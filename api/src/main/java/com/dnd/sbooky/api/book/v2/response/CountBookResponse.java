package com.dnd.sbooky.api.book.v2.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CountBookResponse(@Schema(description = "책의 수") long bookCount) {

    public static CountBookResponse from(long bookCount) {
        return new CountBookResponse(bookCount);
    }
}
