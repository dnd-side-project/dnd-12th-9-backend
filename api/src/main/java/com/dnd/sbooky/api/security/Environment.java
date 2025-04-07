package com.dnd.sbooky.api.security;

import lombok.Getter;

@Getter
public enum Environment {
    LOCAL("local"),
    DEV("dev"),
    PROD("prod");

    private final String value;

    Environment(String value) {
        this.value = value;
    }

    public static Environment fromString(String text) {
        for (Environment env : Environment.values()) {
            if (env.value.equalsIgnoreCase(text)) {
                return env;
            }
        }

        // todo: default 환경 vs. throw Exception..
        return DEV;
    }
}
