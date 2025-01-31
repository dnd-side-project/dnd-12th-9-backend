package com.dnd.sbooky.api.docs.controller;

import com.dnd.sbooky.api.book.response.FindAllBookResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import com.dnd.sbooky.core.book.ReadStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "[Book API]", description = "책 조회에 관련된 API")
@SecurityRequirement(name = "access-token")
public interface FindBookControllerDocs {

    @Operation(summary = "내 도서 전체 조회", description = "회원이 등록한 모든 도서를 조회한다.")
    ApiResponse<FindAllBookResponse> searchBooks(ReadStatus readStatus, UserDetails userDetails);
}
