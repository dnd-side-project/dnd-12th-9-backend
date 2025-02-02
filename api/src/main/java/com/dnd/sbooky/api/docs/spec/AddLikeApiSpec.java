package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.member.reqeust.AddLikeRequest;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[Like API]", description = "좋아요에 관련된 API")
public interface AddLikeApiSpec {

    @Operation(summary = "좋아요 요청", description = "방문자가 좋아요를 누르는 횟수를 반영한다.")
    ApiResponse<?> addLikes(AddLikeRequest request);
}
