package com.dnd.sbooky.api.book.response;

import com.dnd.sbooky.core.book.dto.FindBooksDTO;
import com.dnd.sbooky.core.book.dto.FindBooksDTO.FindBookDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "모든 책 조회 응답 DTO")
public record FindAllBookResponse(
        @Schema(description = "총 등록된 책의 수") long totalBookCount,
        @Schema(description = "책 목록") List<FindBookResponse> bookList) {

    public static FindAllBookResponse of(FindBooksDTO response) {

        return new FindAllBookResponse(
                response.totalBookCount(), response.books().stream().map(FindBookResponse::of).toList());
    }

    private record FindBookResponse(
            @Schema(description = "책 ID") Long id,
            @Schema(description = "책 제목") String title,
            @Schema(description = "저자") String author,
            @Schema(description = "썸네일 URL") String thumbnailUrl,
            @Schema(description = "읽은 상태") String readStatus) {

        public static FindBookResponse of(FindBookDTO book) {
            return new FindBookResponse(
                    book.id(),
                    book.title(),
                    book.author(),
                    book.thumbnailUrl(),
                    book.readStatus().getDescription());
        }
    }
}
