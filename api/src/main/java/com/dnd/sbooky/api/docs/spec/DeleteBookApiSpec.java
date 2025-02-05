package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(
        name = "[Book API]",
        description = "책 삭제에 관련된 API"
)
@SecurityRequirement(name = "access-token")
public interface DeleteBookApiSpec {

    @Operation(summary = "책 삭제", description = "회원이 등록한 책을 삭제한다.")
    ApiResponse<?> deleteBook(Long memberBookId, UserDetails user);

}
