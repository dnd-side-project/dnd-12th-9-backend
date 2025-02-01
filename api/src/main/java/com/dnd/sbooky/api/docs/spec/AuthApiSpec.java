package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "[Auth API]", description = "인증에 관련된 API")
public interface AuthApiSpec {
    @Operation(
            summary = "토큰 재발급",
            description = "refreshToken 검증 후 이상 없을 시 accessToken과 refreshToken을 재발급한다.")
    ApiResponse<?> reissue(String refreshToken, HttpServletResponse response);
}
