package com.dnd.sbooky.api.book.v2.response;

import com.dnd.sbooky.core.book.BookEntity;
import com.dnd.sbooky.core.book.MemberBookEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "책 상세 조회 응답 DTO")
public record FindBookDetailsResponseV2(
        @Schema(description = "책 ID") Long id,
        @Schema(description = "책 제목") String title,
        @Schema(description = "저자") String author,
        @Schema(description = "썸네일 URL") String thumbnailUrl,
        @Schema(description = "읽은 상태") String readStatus,
        @Schema(description = "출판일") LocalDate publishedAt,
        @Schema(description = "생성일") LocalDateTime createdAt,
        @Schema(description = "완독일") LocalDate completedAt,
        @Schema(description = "책장 주인") boolean isOwner) {

    public static FindBookDetailsResponseV2 of(MemberBookEntity memberBook, boolean isOwner) {

        BookEntity book = memberBook.getBookEntity();

        return new FindBookDetailsResponseV2(
                memberBook.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getThumbnailUrl(),
                memberBook.getReadStatus().name(),
                book.getPublishedAt(),
                memberBook.getCreatedAt(),
                memberBook.getCompletedAt(),
                isOwner);
    }
}
