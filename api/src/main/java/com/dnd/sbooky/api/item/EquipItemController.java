package com.dnd.sbooky.api.item;

import com.dnd.sbooky.api.docs.spec.EquipItemApiSpec;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EquipItemController implements EquipItemApiSpec {

    private final EquipItemUsecase equipItemUsecase;

    @PatchMapping("/items/{itemId}")
    public ApiResponse<?> equipItem(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @Parameter @PathVariable Long itemId) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        equipItemUsecase.equipItem(memberId, itemId);
        return ApiResponse.success();
    }
}
