package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.evaluation.request.RegisterEvaluationRequest;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "[Evaluation API]", description = "평가에 관련된 API")
public interface RegisterEvaluationApiSpec {

    @Operation(summary = "평가 등록", description = "완독한 책에 대해 평가를 등록한다.")
    ApiResponse<?> registerEvaluation(
            Long memberBookId, RegisterEvaluationRequest request, UserDetails userDetails);
}
