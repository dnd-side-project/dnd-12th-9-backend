package com.dnd.sbooky.api.member;

import com.dnd.sbooky.api.docs.spec.PerformOnboardingApiSpec;
import com.dnd.sbooky.api.member.request.PerformOnboardingRequest;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PerformOnboardingController implements PerformOnboardingApiSpec {

    private final PerformOnboardingUseCase performOnboardingUseCase;

    @PostMapping("/members/onboarding")
    public ApiResponse<?> performOnboarding(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody PerformOnboardingRequest request) {
        Long memberId = Long.valueOf(userDetails.getUsername());
        performOnboardingUseCase.performOnboarding(memberId, request);
        return ApiResponse.success();
    }
}
