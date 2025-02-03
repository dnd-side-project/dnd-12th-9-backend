package com.dnd.sbooky.api.book.response;

import com.dnd.sbooky.core.book.dto.FindBookDetailsDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "책 상세 조회 응답 DTO")
public record FindBookDetailsResponse(
        @Schema(description = "책 ID") Long id,
        @Schema(description = "책 제목") String title,
        @Schema(description = "저자") String author,
        @Schema(description = "썸네일 URL") String thumbnailUrl,
        @Schema(description = "읽은 상태") String readStatus,
        @Schema(description = "출판일") LocalDate publishedAt,
        @Schema(description = "생성일") LocalDateTime createdAt,
        @Schema(description = "완독일") LocalDate completedAt

        // todo : 책 평가 항목도 추가해야 함!
        ) {

    public static FindBookDetailsResponse of(FindBookDetailsDTO bookDetails) {
        return new FindBookDetailsResponse(
                bookDetails.memberBookId(),
                bookDetails.title(),
                bookDetails.author(),
                bookDetails.thumbnailUrl(),
                bookDetails.readStatus().getDescription(),
                bookDetails.publishedAt(),
                bookDetails.createdAt(),
                bookDetails.completedAt());
    }
}
