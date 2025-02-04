package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "[Like API]", description = "좋아요에 관련된 API")
public interface GetLikesApiSpec {
    @Operation(summary = "방문자용 좋아요 수 조회", description = "방문자가 회원의 좋아요 수를 조회한다.")
    ApiResponse<?> getLikes(Long memberId);

    @SecurityRequirement(name = "access-token")
    @Operation(summary = "호스타용 좋아요 수 조회", description = "호스트가 회원의 좋아요 수를 조회한다.")
    ApiResponse<?> getLikes(UserDetails user);
}
