package com.dnd.sbooky.core.item;

import java.util.Arrays;

public enum ItemCode {
    MUMMY(1L, "mummy_ghost"),
    BASIC(2L, "basic_ghost"),
    CAT(3L, "cat_ghost"),
    WIZARD(4L, "wizard_ghost"),
    DETECTIVE(5L, "detective_ghost"),
    LITTLE_PRINCE(6L, "littleprince_ghost"),
    FRANKENSTEIN(7L, "frankenstein_ghost"),
    REDHOOD(8L, "redhood_ghost"),
    CHEESE_CAT(9L, "cheese_cat_ghost"),
    SIAMESE_CAT(10L, "siamese_cat_ghost"),
    BAEKDO_CAT(11L, "baekdo_cat_ghost");

    private final Long id;
    private final String code;

    ItemCode(Long id, String code) {
        this.id = id;
        this.code = code;
    }

    public static Long toId(String code) {
        return Arrays.stream(values())
                .filter(codes -> codes.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid code: " + code))
                .id;
    }

    public static String toCode(Long id) {
        return Arrays.stream(values())
                .filter(codes -> codes.id.equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id))
                .code;
    }

    public Long getId() {
        return id;
    }
}
