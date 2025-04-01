package com.dnd.sbooky.api.book.v2.response;

import com.dnd.sbooky.core.book.dto.FindBookDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "모든 책 조회 응답 DTO")
public record FindAllBookResponseV2(
        @Schema(description = "총 등록된 책의 수") long totalBookCount,
        @Schema(description = "책 목록") List<FindBookResponse> bookList,
        @Schema(description = "책장 주인") boolean isOwner) {

    public static FindAllBookResponseV2 of(
            long totalBookCount, List<FindBookDTO> response, boolean isOwner) {

        return new FindAllBookResponseV2(
                totalBookCount, response.stream().map(FindBookResponse::of).toList(), isOwner);
    }

    private record FindBookResponse(
            @Schema(description = "책 ID") Long id,
            @Schema(description = "책 제목") String title,
            @Schema(description = "저자") String author,
            @Schema(description = "썸네일 URL") String thumbnailUrl,
            @Schema(description = "읽은 상태") String readStatus) {

        public static FindBookResponse of(FindBookDTO book) {
            return new FindBookResponse(
                    book.id(), book.title(), book.author(), book.thumbnailUrl(), book.readStatus().name());
        }
    }
}
