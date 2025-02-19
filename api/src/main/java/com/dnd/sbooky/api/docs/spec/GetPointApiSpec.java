package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.point.response.GetPointResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "[Point API]", description = "포인트에 관련된 API")
@SecurityRequirement(name = "access-token")
public interface GetPointApiSpec {

    @Operation(summary = "포인트 및 뽑기 정보 조회", description = "회원이 포인트 및 뽑기 정보를 조회한다.")
    ApiResponse<GetPointResponse> getPoint(UserDetails user);
}
