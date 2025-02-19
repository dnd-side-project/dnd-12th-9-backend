package com.dnd.sbooky.api.point.response;

import com.dnd.sbooky.core.point.PointPolicy;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "포인트 및 뽑기 정보 조회 응답")
public record GetPointResponse(
        @Schema(description = "보유 포인트") int point,
        @Schema(description = "뽑기 가능 횟수") int drawCount,
        @Schema(description = "뽑기 1회 소모 포인트") int drawPoint) {

    public static GetPointResponse from(int point) {

        int drawPoint = Math.abs(PointPolicy.DRAW_ITEM.getPoint());
        int drawCount = point / drawPoint;

        return new GetPointResponse(point, drawCount, drawPoint);
    }
}
