package com.dnd.sbooky.core.evaluation;

import lombok.Getter;


@Getter
public enum EvaluationKeyword {
    IMMERSIVE(101L, EvaluationType.GOOD, "몰입감 높은"),
    EMOTIONAL(102L, EvaluationType.GOOD, "감동적인"),
    INFORMATIVE(103L, EvaluationType.GOOD, "유익한"),
    BEAUTIFUL_SENTENCE(104L, EvaluationType.GOOD, "문장이 아름다운"),
    EASY_TO_READ(105L, EvaluationType.GOOD, "읽기 부담없는"),
    LONG_LASTING_AFTERGLOW(106L, EvaluationType.GOOD, "여운이 오래가는"),
    RECOMMENDABLE(107L, EvaluationType.GOOD, "추천하고 싶은"),
    KNOWLEDGEABLE(108L, EvaluationType.GOOD, "새로운 지식을 주는"),

    BORING(201L, EvaluationType.SHAME, "지루한"),
    DISAPPOINTING(202L, EvaluationType.SHAME, "기대보다 아쉬운"),
    LACK_OF_COHERENCE(203L, EvaluationType.SHAME, "개연성이 부족한"),
    DIFFICULT_SENTENCE(204L, EvaluationType.SHAME, "문장이 난해한"),
    UNSATISFYING_ENDING(205L, EvaluationType.SHAME, "결말이 아쉬운");

    private final Long id;
    private final EvaluationType type;
    private final String description;

    EvaluationKeyword(Long id, EvaluationType type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }
}
