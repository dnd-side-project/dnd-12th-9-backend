package com.dnd.sbooky.api.book.response;

import com.dnd.sbooky.clients.kakao.response.KakaoSearchBookResponseDTO;
import com.dnd.sbooky.clients.kakao.response.KakaoSearchBookResponseDTO.Meta;
import java.time.LocalDate;
import java.util.List;

public record SearchBookResponse(List<Book> books, PageInfo pageInfo) {

    // spotless:off
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
    // spotless:on

    public record Book(String title, List<String> authors, LocalDate publishedAt, String thumbnail) {}

    public record PageInfo(boolean isEnd, int pageableCount, int totalCount) {}
}
