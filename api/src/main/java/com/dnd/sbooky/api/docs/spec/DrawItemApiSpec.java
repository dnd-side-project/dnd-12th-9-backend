package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.item.response.DrawItemResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "[Item API]", description = "아이템 관련된 API")
@SecurityRequirement(name = "access-token")
public interface DrawItemApiSpec {
    @Operation(summary = "아이템 뽑기", description = "아이템을 랜덤을 뽑는다.")
    ApiResponse<DrawItemResponse> drawItem(UserDetails user);
}
