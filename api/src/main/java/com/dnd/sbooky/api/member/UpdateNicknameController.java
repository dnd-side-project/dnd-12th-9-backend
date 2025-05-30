package com.dnd.sbooky.api.member;

import com.dnd.sbooky.api.docs.spec.UpdateNicknameApiSpec;
import com.dnd.sbooky.api.member.request.UpdateNicknameRequest;
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
public class UpdateNicknameController implements UpdateNicknameApiSpec {

    private final UpdateNicknameUseCase updateNicknameUseCase;

    @PatchMapping("/members/nickname")
    public ApiResponse<?> updateNickname(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody UpdateNicknameRequest request) {

        Long memberId = Long.valueOf(user.getUsername());
        updateNicknameUseCase.updateNickname(memberId, request);

        return ApiResponse.success();
    }
}
