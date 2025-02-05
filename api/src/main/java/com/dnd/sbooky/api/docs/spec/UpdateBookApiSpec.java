package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.book.request.UpdateBookRequest;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "[Book API]", description = "책에 관련된 API")
@SecurityRequirement(name = "access-token")
public interface UpdateBookApiSpec {

    @Operation(summary = "책 수정", description = "회원이 등록한 책을 수정한다.")
    ApiResponse<?> updateBook(UserDetails userDetails, Long memberBookId, UpdateBookRequest request);
}
