package com.dnd.sbooky.api.book.response;

import com.dnd.sbooky.core.book.dto.BookResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "모든 책 조회 응답 DTO")
public record FindAllBookResponse(@Schema(description = "책 목록") List<FindBookResponse> bookList) {
    public static FindAllBookResponse of(List<BookResponseDTO> response) {
        return new FindAllBookResponse(response.stream().map(FindBookResponse::of).toList());
    }

    private record FindBookResponse(
            @Schema(description = "책 ID") Long id,
            @Schema(description = "책 제목") String title,
            @Schema(description = "저자") String author,
            @Schema(description = "썸네일 URL") String thumbnailUrl,
            @Schema(description = "읽은 상태") String readStatus) {

        public static FindBookResponse of(BookResponseDTO book) {
            return new FindBookResponse(
                    book.id(),
                    book.title(),
                    book.author(),
                    book.thumbnailUrl(),
                    book.readStatus().getDescription());
        }
    }
}
