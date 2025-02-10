package com.dnd.sbooky.core.evaluation.dto;

import com.dnd.sbooky.core.evaluation.EvaluationKeyword;
import com.dnd.sbooky.core.evaluation.EvaluationType;

public record GetEvaluationDTO(
        Long evaluationId, EvaluationType type, EvaluationKeyword keyword, boolean isSelected) {}
