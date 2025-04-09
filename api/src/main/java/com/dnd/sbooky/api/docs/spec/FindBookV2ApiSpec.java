package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.book.v2.response.FindAllBookResponseV2;
import com.dnd.sbooky.api.book.v2.response.FindBookCountResponseV2;
import com.dnd.sbooky.api.book.v2.response.FindBookDetailsResponseV2;
import com.dnd.sbooky.api.support.response.ApiResponse;
import com.dnd.sbooky.core.book.ReadStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "[Book API v2]", description = "책에 관련된 API v2")
public interface FindBookV2ApiSpec {

    @Operation(summary = "책장 주인 도서 전체 조회", description = "주인이 등록한 모든 도서를 조회한다.")
    ApiResponse<FindAllBookResponseV2> findBooks(
            Long ownerId, ReadStatus readStatus, UserDetails user);

    @Operation(summary = "책 상태에 따른 개수 조회", description = "책장 주인이 등록한 도서의 개수를 조회한다.")
    ApiResponse<FindBookCountResponseV2> findBookCount(
            Long ownerId, ReadStatus readStatus, UserDetails user);

    @Operation(summary = "책 상세 조회", description = "도서의 상세 정보를 조회한다.")
    ApiResponse<FindBookDetailsResponseV2> findBookDetails(UserDetails user, Long memberBookId);
}
