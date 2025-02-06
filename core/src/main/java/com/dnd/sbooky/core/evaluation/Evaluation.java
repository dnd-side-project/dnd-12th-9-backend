package com.dnd.sbooky.core.evaluation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "evaluation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Evaluation {

    private static final String ENTITY_PREFIX = "evaluation";

    @Id
    @Column(name = ENTITY_PREFIX + "_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = ENTITY_PREFIX + "_type", nullable = false)
    private EvaluationType type;

    @Enumerated(EnumType.STRING)
    @Column(name = ENTITY_PREFIX + "_keyword", nullable = false)
    private EvaluationKeyword keyword;

    private Evaluation(Long id, EvaluationType type, EvaluationKeyword keyword) {
        precondition(id, type, keyword);
        this.id = id;
        this.type = type;
        this.keyword = keyword;
    }

    public static Evaluation newInstance(EvaluationKeyword keyword) {
        return new Evaluation(keyword.getId(), keyword.getType(), keyword);
    }

    private static void precondition(Long id, EvaluationType type, EvaluationKeyword keyword) {
        if (type != keyword.getType()) {
            throw new IllegalStateException("Both type and keyword must be the same.");
        }

        if (!type.isInRange(id)) {
            throw new IllegalArgumentException("Id is out of range.");
        }
    }

}
