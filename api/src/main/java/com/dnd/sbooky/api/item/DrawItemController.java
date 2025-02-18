package com.dnd.sbooky.api.item;

import com.dnd.sbooky.api.docs.spec.DrawItemApiSpec;
import com.dnd.sbooky.api.item.response.DrawItemResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DrawItemController implements DrawItemApiSpec {

    private final DrawItemUsecase drawItemUsecase;

    @PostMapping("/items/member")
    public ApiResponse<DrawItemResponse> drawItem(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {
        Long memberId = extractMemberId(user);
        return ApiResponse.success(drawItemUsecase.drawItem(memberId));
    }

    private Long extractMemberId(UserDetails user) {
        return Long.valueOf(user.getUsername());
    }
}
