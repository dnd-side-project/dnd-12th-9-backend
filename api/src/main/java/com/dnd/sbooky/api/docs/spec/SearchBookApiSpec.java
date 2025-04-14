package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.book.v1.response.SearchBookResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Tag(name = "[Book API]", description = "책에 관련된 API")
public interface SearchBookApiSpec {

    @Operation(summary = "책 검색", description = "책을 검색한다.")
    ApiResponse<SearchBookResponse> searchBook(
            @NotBlank(message = "검색어를 입력해주세요.") String query,
            @Range(min = 1, max = 50, message = "페이지는 1 ~ 50 사이의 값이어야 합니다.") int page);
}
