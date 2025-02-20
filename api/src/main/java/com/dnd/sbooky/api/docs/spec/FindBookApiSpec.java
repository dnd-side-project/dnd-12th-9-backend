package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.book.response.FindAllBookResponse;
import com.dnd.sbooky.api.book.response.FindBookDetailsResponse;
import com.dnd.sbooky.api.book.response.FindCompletedBookResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import com.dnd.sbooky.core.book.ReadStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "[Book API]", description = "책에 관련된 API")
public interface FindBookApiSpec {

    @Operation(summary = "책장 주인 도서 전체 조회", description = "주인이 등록한 모든 도서를 조회한다.")
    ApiResponse<FindAllBookResponse> findBooks(
            Long memberId, ReadStatus readStatus, UserDetails user);

    @Operation(summary = "책 상세 조회", description = "도서의 상세 정보를 조회한다.")
    ApiResponse<FindBookDetailsResponse> findBookDetails(Long memberBookId);

    @Operation(summary = "책장 주인 완독 도서 개수 조회", description = "주인이 등록한 책 중 완독한 도서의 개수를 조회한다.")
    ApiResponse<FindCompletedBookResponse> findCompletedBooks(Long memberId, UserDetails user);
}
