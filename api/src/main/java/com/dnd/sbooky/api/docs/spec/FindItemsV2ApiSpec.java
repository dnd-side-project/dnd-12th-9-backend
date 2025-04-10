package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.item.v1.response.FindMemberEquippedItemsResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[Item API v2]", description = "아이템 관련된 API v2")
public interface FindItemsV2ApiSpec {
    @Operation(summary = "내가 장착한 아이템 조회", description = "내가 장착한 아이템 정보를 조회한다.")
    ApiResponse<FindMemberEquippedItemsResponse> findEquippedItems(Long ownerId);
}
