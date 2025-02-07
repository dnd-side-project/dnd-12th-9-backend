package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.evaluation.response.GetEvaluationResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "[Evaluation API]", description = "평가에 관련된 API")
public interface GetEvaluationApiSpec {

    @Operation(summary = "평가 리스트 조회", description = "평가 리스트를 조회한다.")
    ApiResponse<List<GetEvaluationResponse>> getEvaluation(Long memberBookId, UserDetails user);
}
