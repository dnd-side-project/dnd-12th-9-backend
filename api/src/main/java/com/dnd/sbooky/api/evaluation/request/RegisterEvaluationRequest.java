package com.dnd.sbooky.api.evaluation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Schema(description = "평가 등록 요청 DTO")
public record RegisterEvaluationRequest(
        @Schema(description = "평가 키워드 아이디 리스트")
                @NotNull @Size(min = 1, max = 6, message = "평가 키워드는 1개 이상 6개 이하로 등록해주세요.") List<Long> keywordIds) {}
