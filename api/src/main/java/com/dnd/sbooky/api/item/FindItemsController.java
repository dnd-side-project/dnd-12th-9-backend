package com.dnd.sbooky.api.item;

import com.dnd.sbooky.api.docs.spec.FindItemsApiSpec;
import com.dnd.sbooky.api.item.response.FindEquippedItemsResponse;
import com.dnd.sbooky.api.item.response.FindMemberEquippedItemsResponse;
import com.dnd.sbooky.api.item.response.FindItemsResponse;
import com.dnd.sbooky.api.member.FindNicknameUsecase;
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
    private final FindNicknameUsecase findNicknameUsecase;

    @GetMapping("/items")
    public ApiResponse<FindItemsResponse> findMyItems(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {
        return ApiResponse.success(findItemsUseCase.findMyItems(extractMemberId(user)));
    }

    @GetMapping("/items/equipped")
    public ApiResponse<FindMemberEquippedItemsResponse> findEquippedItems(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {
        Long memberId = extractMemberId(user);
        FindEquippedItemsResponse equippedItemsDTO = findEquippedItemUsecase.findEquippedItems(memberId);
        String nickname = findNicknameUsecase.findNickname(memberId);
        return ApiResponse.success(FindMemberEquippedItemsResponse.from(equippedItemsDTO, nickname));
    }

    private Long extractMemberId(UserDetails user) {
        return Long.valueOf(user.getUsername());
    }
}
