package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.member.request.UpdateNicknameRequest;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "[Member API]", description = "회원 관련 API")
public interface UpdateNicknameApiSpec {

    @Operation(summary = "닉네임 변경", description = "회원의 닉네임을 변경한다.")
    ApiResponse<?> updateNickname(UserDetails user, UpdateNicknameRequest request);
}
