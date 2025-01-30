package com.dnd.sbooky.api.security;

public enum SameSite {
    STRICT("Strict"),
    LAX("Lax"),
    NONE("None");

    private final String value;

    SameSite(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
