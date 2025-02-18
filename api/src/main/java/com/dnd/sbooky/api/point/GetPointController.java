package com.dnd.sbooky.api.point;

import com.dnd.sbooky.api.docs.spec.GetPointApiSpec;
import com.dnd.sbooky.api.point.response.GetPointResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GetPointController implements GetPointApiSpec {

    private final GetPointUseCase getPointUseCase;

    @GetMapping("/members/point")
    public ApiResponse<GetPointResponse> getPoint(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {

        // fixme : 해당 패키지 위치가 맞는가?
        Long memberId = Long.valueOf(user.getUsername());
        return ApiResponse.success(getPointUseCase.get(memberId));
    }
}
