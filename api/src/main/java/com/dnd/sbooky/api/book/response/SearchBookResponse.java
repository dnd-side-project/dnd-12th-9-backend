package com.dnd.sbooky.api.book.response;

import com.dnd.sbooky.clients.kakao.response.KakaoSearchBookResponseDTO;
import com.dnd.sbooky.clients.kakao.response.KakaoSearchBookResponseDTO.Meta;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

@Schema(name = "SearchBookResponse", description = "책 검색 결과")
public record SearchBookResponse(
        // spotless:off
        @Schema(name = "책 목록", description = "검색된 책 목록")
        List<Book> books,

        @Schema(name = "페이지 정보", description = "페이지 정보")
        PageInfo pageInfo) {

    public static SearchBookResponse from(KakaoSearchBookResponseDTO dto) {
        List<Book> books = dto.documents().stream()
                              .map(document -> new Book(
                                      document.title(),
                                      document.authors(),
                                      document.datetime() == null
                                              ? LocalDate.EPOCH : document.datetime().toLocalDate(),
                                      document.extractThumbnailFileName())
                              ).toList();

        Meta meta = dto.meta();
        PageInfo pageInfo = new PageInfo(meta.is_end(), meta.pageable_count(), meta.total_count());

        return new SearchBookResponse(books, pageInfo);
    }

    public record Book(
            @Schema(name = "책 제목") String title,
            @Schema(name = "저자 리스트") List<String> authors,
            @Schema(name = "출판일") LocalDate publishedAt,
            @Schema(name = "썸네일 URL") String thumbnail) {

    }

    public record PageInfo(
            @Schema(name = "마지막 페이지") boolean isEnd,
            @Schema(name = "중복된 책 제외 노출 가능 책 수") int pageableCount,
            @Schema(name = "검색된 책의 수") int totalCount) {}
    // spotless:on

}
