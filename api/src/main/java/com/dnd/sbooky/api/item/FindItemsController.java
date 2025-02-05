package com.dnd.sbooky.api.item;

import com.dnd.sbooky.api.docs.spec.FindItemsApiSpec;
import com.dnd.sbooky.api.item.response.FindEquippedItemsResponse;
import com.dnd.sbooky.api.item.response.FindItemsResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FindItemsController implements FindItemsApiSpec {

    private final FindItemsUseCase findItemsUseCase;
    private final FindEquippedItemUsecase findEquippedItemUsecase;

    @GetMapping("/items")
    public ApiResponse<FindItemsResponse> findMyItems(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {
        return ApiResponse.success(findItemsUseCase.findMyItems(extractMemberId(user)));
    }

    @GetMapping("/items/equipped")
    public ApiResponse<FindEquippedItemsResponse> findEquippedItems(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {
        return ApiResponse.success(
                findEquippedItemUsecase.findEquippedItems(extractMemberId(user)));
    }

    private Long extractMemberId(UserDetails user) {
        return Long.valueOf(user.getUsername());
    }
}
