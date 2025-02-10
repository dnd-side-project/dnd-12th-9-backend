package com.dnd.sbooky.api.book.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

@Schema(description = "책 검색 요청 DTO")
public record SearchBookRequest(
        @Schema(description = "검색어") @NotBlank(message = "검색어를 입력해주세요.") String query,
        @Schema(
                        description = "정렬 방식",
                        defaultValue = "accuracy",
                        allowableValues = {"accuracy", "latest"},
                        nullable = true)
                @Pattern(regexp = "^(accuracy|latest)$", message = "정렬 방식은 accuracy 또는 latest 중 하나여야 합니다.")
                String sort,
        @Schema(
                        description = "결과 페이지 번호",
                        defaultValue = "1",
                        minLength = 1,
                        maxLength = 50,
                        nullable = true)
                @Range(min = 1, max = 50, message = "페이지는 1 ~ 50 사이의 값이어야 합니다.")
                Integer page,
        @Schema(
                        description = "한 페이지당 문서 수",
                        defaultValue = "10",
                        minLength = 1,
                        maxLength = 50,
                        nullable = true)
                @Range(min = 1, max = 50, message = "한 페이지당 문서 수는 1 ~ 50 사이의 값이어야 합니다.")
                Integer size,
        @Schema(
                        description = "검색 필드",
                        allowableValues = {"title", "isbn", "publisher", "person"},
                        nullable = true)
                @Pattern(
                        regexp = "^(title|isbn|publisher|person)$",
                        message = "검색 필드는 title, isbn, publisher, person 중 하나여야 합니다.")
                String target) {

    /**
     * Compact Constructor
     */
    public SearchBookRequest {
        sort = sort == null ? "accuracy" : sort;
        page = page == null ? 1 : page;
        size = size == null ? 10 : size;
    }
}
