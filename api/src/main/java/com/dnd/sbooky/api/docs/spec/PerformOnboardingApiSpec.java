package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.member.request.PerformOnboardingRequest;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "[Member API]", description = "회원에 관련된 API")
@SecurityRequirement(name = "access-token")
public interface PerformOnboardingApiSpec {
    @Operation(summary = "회원 온보딩", description = "회원을 온보딩한다.")
    ApiResponse<?> performOnboarding(UserDetails user, PerformOnboardingRequest request);
}
