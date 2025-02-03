package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.book.response.FindAllBookResponse;
import com.dnd.sbooky.api.book.response.FindBookDetailsResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import com.dnd.sbooky.core.book.ReadStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[Book API]", description = "책 조회에 관련된 API")
@SecurityRequirement(name = "access-token")
public interface FindBookApiSpec {

    @Operation(summary = "책장 주인 도서 전체 조회", description = "주인이 등록한 모든 도서를 조회한다.")
    ApiResponse<FindAllBookResponse> findBooks(Long memberId, ReadStatus readStatus);

    @Operation(summary = "책 상세 조회", description = "도서의 상세 정보를 조회한다.")
    ApiResponse<FindBookDetailsResponse> findBookDetails(Long memberBookId);
}
