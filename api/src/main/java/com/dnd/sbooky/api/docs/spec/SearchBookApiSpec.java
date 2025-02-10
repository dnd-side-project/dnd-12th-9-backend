package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.book.request.SearchBookRequest;
import com.dnd.sbooky.api.book.response.SearchBookResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[Book API]", description = "책에 관련된 API")
public interface SearchBookApiSpec {

    @Operation(summary = "책 검색 v1", description = "책을 검색한다. (ModelAttribute)")
    ApiResponse<SearchBookResponse> searchBook(SearchBookRequest request);

    @Operation(summary = "책 검색 v2", description = "책을 검색한다. (RequestParam [Query Param])")
    ApiResponse<SearchBookResponse> searchBookV2(
            String query, String sort, int size, int page, String target);
}
