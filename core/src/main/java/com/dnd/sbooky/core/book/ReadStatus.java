package com.dnd.sbooky.core.book;

public enum ReadStatus {
    WANT_TO_READ,   // 읽고 싶어요
    READING,        // 읽고 있어요
    COMPLETED;      // 다 읽었어요

    public static ReadStatus convert(String status) {

        return switch (status) {
            case "want" -> WANT_TO_READ;
            case "reading" -> READING;
            case "complete" -> COMPLETED;
            default -> null;
        };
    }
}
