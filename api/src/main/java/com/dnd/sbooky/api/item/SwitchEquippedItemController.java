package com.dnd.sbooky.api.item;

import com.dnd.sbooky.api.docs.spec.SwitchEquippedItemApiSpec;
import com.dnd.sbooky.api.item.request.SwitchEquippedItemRequest;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SwitchEquippedItemController implements SwitchEquippedItemApiSpec {

    private final SwitchEquippedItemUsecase switchEquippedItemUsecase;

    @PatchMapping("/items")
    public ApiResponse<?> switchEqiuppedItem(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody SwitchEquippedItemRequest request) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        switchEquippedItemUsecase.switchEquippedItem(
                memberId, request.equippedItemCode(), request.toEquipItemCode());
        return ApiResponse.success();
    }
}
