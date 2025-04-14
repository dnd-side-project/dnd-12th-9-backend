package com.dnd.sbooky.api.book.v1.response;

import com.dnd.sbooky.clients.api.response.SearchBookDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

@Schema(name = "SearchBookResponse", description = "책 검색 결과")
public record SearchBookResponse(

        // spotless:off
        @Schema(description = "검색된 책 목록")
        List<Book> books,

        @Schema(description = "페이지 정보")
        PageInfo pageInfo) {

    public static SearchBookResponse from(SearchBookDTO dto) {
        List<Book> books = dto.books().stream()
                              .map(book -> new Book(
                                      book.title(),
                                      book.author(),
                                      book.publishedAt(),
                                      book.thumbnail()))
                              .toList();

        PageInfo pageInfo = new PageInfo(
                dto.pageInfo().isEnd(),
                dto.pageInfo().pageableCount(),
                dto.pageInfo().totalCount(),
                dto.pageInfo().page());

        return new SearchBookResponse(books, pageInfo);
    }

    public record Book(
            @Schema(description = "책 제목") String title,
            @Schema(description = "저자") String author,
            @Schema(description = "출판일") LocalDate publishedAt,
            @Schema(description = "썸네일 URL") String thumbnail) {

    }

    public record PageInfo(
            @Schema(description = "마지막 페이지") boolean isEnd,
            @Schema(description = "중복된 책 제외 노출 가능 책 수") int pageableCount,
            @Schema(description = "검색된 책의 수") int totalCount,
            @Schema(description = "현재 페이지 번호") int page) {
    }

    // spotless:on

}
