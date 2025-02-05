package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.item.response.FindItemsResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "[Item API]", description = "아이템 관련된 API")
@SecurityRequirement(name = "access-token")
public interface FindItemsApiSpec {
    @Operation(summary = "내 아이템 조회", description = "내 아이템 정보를 조회한다.")
    ApiResponse<FindItemsResponse> findMyItems(UserDetails user);

    @Operation(summary = "내가 장착한 아이템 조회", description = "내가 장착한 아이템 정보를 조회한다.")
    ApiResponse<FindItemsResponse> findEquippedItems(UserDetails user);
}
