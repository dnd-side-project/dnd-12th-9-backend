package com.dnd.sbooky.api.evaluation;

import com.dnd.sbooky.api.docs.spec.GetEvaluationApiSpec;
import com.dnd.sbooky.api.evaluation.response.GetEvaluationResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GetEvaluationController implements GetEvaluationApiSpec {

    private final GetEvaluationUseCase getEvaluationUseCase;

    @GetMapping("/books/{memberBookId}/evaluation")
    public ApiResponse<List<GetEvaluationResponse>> getEvaluation(
            @PathVariable Long memberBookId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {

        Long memberId = getMemberId(user);
        return ApiResponse.success(getEvaluationUseCase.get(memberId, memberBookId));
    }

    private Long getMemberId(UserDetails user) {
        return Long.valueOf(user.getUsername());
    }
}
