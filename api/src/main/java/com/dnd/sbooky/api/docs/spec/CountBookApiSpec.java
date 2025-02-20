package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.book.response.CountCompletedBookResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "[Book API]", description = "책에 관련된 API")
public interface CountBookApiSpec {

    @Operation(summary = "책장 주인 완독 도서 개수 조회", description = "주인이 등록한 책 중 완독한 도서의 개수를 조회한다.")
    ApiResponse<CountCompletedBookResponse> countCompletedBooks(Long memberId, UserDetails user);
}
