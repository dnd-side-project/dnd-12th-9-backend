package com.dnd.sbooky.core.item;

import java.util.Arrays;

public enum ItemCode {
    MUMMY(1L, "mummy_ghost"),
    BASIC(2L, "basic_ghost");

    private final Long id;
    private final String code;

    ItemCode(Long id, String code) {
        this.id = id;
        this.code = code;
    }

    public static Long toCode(String code){
        return Arrays.stream(values())
                .filter(codes -> codes.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid code: " + code)).id;
    }
}
