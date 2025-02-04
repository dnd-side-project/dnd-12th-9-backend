package com.dnd.sbooky.api.like.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "좋아요 수 조회 응답 DTO")
public record GetLikesResponse(@Schema(description = "좋아요 수") Long count) {
    public static GetLikesResponse from(Long count) {
        return new GetLikesResponse(count);
    }
}
