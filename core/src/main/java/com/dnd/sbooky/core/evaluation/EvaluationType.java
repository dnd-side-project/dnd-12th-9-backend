package com.dnd.sbooky.core.evaluation;

import lombok.Getter;

@Getter
public enum EvaluationType {
    GOOD(100L, "좋았어요"),
    SHAME(200L, "아쉬웠어요");

    private final Long idPrefix;
    private final String description;

    EvaluationType(Long idPrefix, String description) {
        this.idPrefix = idPrefix;
        this.description = description;
    }

    public boolean isInRange(Long id) {
        return id >= idPrefix && id < idPrefix + 100L;
    }
}
