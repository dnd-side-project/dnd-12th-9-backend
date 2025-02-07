package com.dnd.sbooky.api.evaluation;

import com.dnd.sbooky.api.evaluation.request.RegisterEvaluationRequest;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegisterEvaluationController {

    private final RegisterEvaluationUseCase registerEvaluationUseCase;

    @PostMapping("/books/{memberBookId}/evaluation")
    public ApiResponse<?> registerEvaluation(
            @PathVariable Long memberBookId,
            @Valid @RequestBody RegisterEvaluationRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {

        registerEvaluationUseCase.register(memberBookId, extractMemberId(user), request);
        return ApiResponse.success();
    }

    private Long extractMemberId(UserDetails user) {
        return Long.valueOf(user.getUsername());
    }
}
