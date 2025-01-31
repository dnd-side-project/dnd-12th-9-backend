package com.dnd.sbooky.core.book;

public enum ReadStatus {
    WANT_TO_READ,   // 읽고 싶어요
    READING,        // 읽고 있어요
    COMPLETED;      // 다 읽었어요

    public static ReadStatus create(String resource) {

        for (ReadStatus readStatus : ReadStatus.values()) {
            if (readStatus.name().equals(resource)) {
                return readStatus;
            }
        }

        // TODO: 예외 처리 커스터마이징 필요
        throw new IllegalArgumentException("해당하는 ReadStatus가 없습니다.");
    }
}
