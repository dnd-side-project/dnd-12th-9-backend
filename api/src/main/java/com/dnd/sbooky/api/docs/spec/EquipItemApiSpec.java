package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "[Item API]", description = "아이템에 관련된 API")
@SecurityRequirement(name = "access-token")
public interface EquipItemApiSpec {
    @Operation(summary = "아이템 장착", description = "아이템을 장착한다.")
    ApiResponse<?> equipItem(UserDetails user, Long itemId);
}
