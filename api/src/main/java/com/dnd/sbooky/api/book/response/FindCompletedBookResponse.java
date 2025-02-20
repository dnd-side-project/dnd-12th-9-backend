package com.dnd.sbooky.api.book.response;

public record FindCompletedBookResponse(long completedBookCount) {

    public static FindCompletedBookResponse of(long completedBookCount) {
        return new FindCompletedBookResponse(completedBookCount);
    }
}
