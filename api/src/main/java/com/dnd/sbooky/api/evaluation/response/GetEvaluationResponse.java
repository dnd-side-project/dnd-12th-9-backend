package com.dnd.sbooky.api.evaluation.response;

import com.dnd.sbooky.core.evaluation.EvaluationKeyword;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "평가 리스트 조회 응답")
public record GetEvaluationResponse(
        @Schema(description = "평가 ID") Long evaluationId,
        @Schema(description = "평가 타입") String type,
        @Schema(description = "평가 키워드") String keyword) {

    public static GetEvaluationResponse of(EvaluationKeyword keyword) {
        return new GetEvaluationResponse(
                keyword.getId(), keyword.getType().getDescription(), keyword.getDescription());
    }
}
