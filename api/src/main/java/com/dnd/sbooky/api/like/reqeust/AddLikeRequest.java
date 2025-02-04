package com.dnd.sbooky.api.like.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "좋아요 요청 DTO")
public record AddLikeRequest(
        @Schema(description = "주인 식별자") @NotNull Long memberId,
        @Schema(description = "추가할 좋아요 수") @NotNull Long addCount) {}
