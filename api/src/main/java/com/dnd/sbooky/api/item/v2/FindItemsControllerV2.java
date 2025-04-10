package com.dnd.sbooky.api.item.v2;

import com.dnd.sbooky.api.docs.spec.FindItemsV2ApiSpec;
import com.dnd.sbooky.api.item.v1.FindEquippedItemUsecase;
import com.dnd.sbooky.api.item.v1.response.FindEquippedItemsResponse;
import com.dnd.sbooky.api.item.v2.response.FindMemberEquippedItemsResponseV2;
import com.dnd.sbooky.api.member.FindNicknameUsecase;
import com.dnd.sbooky.api.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FindItemsControllerV2 implements FindItemsV2ApiSpec {

    private final FindEquippedItemUsecase findEquippedItemUsecase;
    private final FindNicknameUsecase findNicknameUsecase;

    @GetMapping("/members/{ownerId}/items/equipped")
    public ApiResponse<FindMemberEquippedItemsResponseV2> findEquippedItems(
            @PathVariable Long ownerId) {
        FindEquippedItemsResponse equippedItemsDTO = findEquippedItemUsecase.findEquippedItems(ownerId);
        String nickname = findNicknameUsecase.findNickname(ownerId);
        return ApiResponse.success(FindMemberEquippedItemsResponseV2.from(equippedItemsDTO, nickname));
    }
}
