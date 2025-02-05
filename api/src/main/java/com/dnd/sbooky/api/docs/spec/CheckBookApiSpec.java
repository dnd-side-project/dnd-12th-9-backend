package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.book.response.CheckBookResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "[Book API]", description = "책에 관련된 API")
@SecurityRequirement(name = "access-token")
public interface CheckBookApiSpec {

    @Operation(summary = "주인에게 책이 존재하는지 확인", description = "주인의 책장에 등록하려는 책이 이미 책이 존재하는지 확인한다.")
    ApiResponse<CheckBookResponse> checkBookExist(UserDetails user, String title, String author);
}
