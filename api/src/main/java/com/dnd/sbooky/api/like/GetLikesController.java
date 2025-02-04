package com.dnd.sbooky.api.like;

import com.dnd.sbooky.api.docs.spec.GetLikesApiSpec;
import com.dnd.sbooky.api.like.response.GetLikesResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
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
public class GetLikesController implements GetLikesApiSpec {

    private final GetLikesUsecase getLikesUsecase;

    @GetMapping("/likes/{memberId}")
    public ApiResponse<?> getLikes(@PathVariable Long memberId) {
        return ApiResponse.success(GetLikesResponse.from(getLikesUsecase.get(memberId)));
    }

    @GetMapping("/likes")
    public ApiResponse<?> getLikes(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {
        return ApiResponse.success(
                GetLikesResponse.from(getLikesUsecase.get(Long.valueOf(user.getUsername()))));
    }
}
