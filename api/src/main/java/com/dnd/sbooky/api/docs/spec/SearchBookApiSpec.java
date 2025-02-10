package com.dnd.sbooky.api.docs.spec;

import com.dnd.sbooky.api.book.response.SearchBookResponse;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

@Tag(name = "[Book API]", description = "책에 관련된 API")
public interface SearchBookApiSpec {

    // spotless:off
    @Operation(summary = "책 검색", description = "책을 검색한다.")
    ApiResponse<SearchBookResponse> searchBook(
            @NotBlank(message = "검색어를 입력해주세요.")
            String query,

            @Pattern(regexp = "^(accuracy|latest)$",
                    message = "정렬 방식은 accuracy 또는 latest 중 하나여야 합니다.")
            String sort,

            @Range(min = 1, max = 50,
                    message = "페이지는 1 ~ 50 사이의 값이어야 합니다.")
            int page,

            @Range(min = 1, max = 50,
                    message = "한 페이지당 문서 수는 1 ~ 50 사이의 값이어야 합니다.")
            int size,

            @Pattern(regexp = "^(title|isbn|publisher|person)$",
                    message = "검색 필드는 title, isbn, publisher, person 중 하나여야 합니다.")
            String target
    );
    // spotless:on

}
