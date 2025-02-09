package com.dnd.sbooky.api.evaluation;

import com.dnd.sbooky.api.docs.spec.RegisterEvaluationApiSpec;
import com.dnd.sbooky.api.evaluation.request.RegisterEvaluationRequest;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegisterEvaluationController implements RegisterEvaluationApiSpec {

    private final RegisterEvaluationUseCase registerEvaluationUseCase;

    @PutMapping("/books/{memberBookId}/evaluation")
    @ResponseStatus(HttpStatus.CREATED)
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
