package com.dnd.sbooky.core.book;

import lombok.Getter;

@Getter
public enum ReadStatus {
    WANT_TO_READ("읽기 전"),
    READING("읽는 중"),
    COMPLETED("완독");

    private final String description;

    ReadStatus(String description) {
        this.description = description;
    }

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
