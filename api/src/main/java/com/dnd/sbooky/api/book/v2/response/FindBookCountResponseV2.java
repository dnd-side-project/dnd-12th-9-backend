package com.dnd.sbooky.api.book.v2.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record FindBookCountResponseV2(@Schema(description = "책의 수") long bookCount) {

    public static FindBookCountResponseV2 from(long bookCount) {
        return new FindBookCountResponseV2(bookCount);
    }
}
