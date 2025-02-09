package com.dnd.sbooky.api.evaluation.response;

import com.dnd.sbooky.core.evaluation.dto.GetEvaluationDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "평가 리스트 조회 응답")
public record GetEvaluationResponse(
        @Schema(description = "평가 ID") Long evaluationId,
        @Schema(description = "평가 타입") String type,
        @Schema(description = "평가 키워드") String keyword,
        @Schema(description = "선택 여부") boolean isSelected) {

    public static GetEvaluationResponse of(GetEvaluationDTO dto) {
        return new GetEvaluationResponse(
                dto.evaluationId(),
                dto.type().getDescription(),
                dto.keyword().getDescription(),
                dto.isSelected());
    }
}
