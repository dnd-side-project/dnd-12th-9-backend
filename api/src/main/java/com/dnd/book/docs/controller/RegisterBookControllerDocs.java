package com.dnd.book.docs.controller;

import com.dnd.book.book.request.RegisterBookRequest;
import com.dnd.book.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "[Book API]",
        description = "책 등록에 관련된 API"
)
public interface RegisterBookControllerDocs {

    @Operation(summary = "책 등록", description = "회원이 새로운 책을 등록한다.")

    ApiResponse<?> registerBook(RegisterBookRequest request);

}
