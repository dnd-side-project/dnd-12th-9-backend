package com.dnd.sbooky.api.book.response;

import com.dnd.sbooky.clients.kakao.response.KakaoSearchBookResponseDTO;
import com.dnd.sbooky.clients.kakao.response.KakaoSearchBookResponseDTO.Document;
import com.dnd.sbooky.clients.kakao.response.KakaoSearchBookResponseDTO.Meta;
import java.time.LocalDate;
import java.util.List;

public record SearchBookResponse(List<Book> books, PageInfo pageInfo) {

    public static SearchBookResponse from(KakaoSearchBookResponseDTO dto) {
        List<Document> documents = dto.documents();
        List<Book> books =
                documents.stream()
                        .map(
                                document ->
                                        new Book(
                                                document.title(),
                                                document.authors(),
                                                document.datetime() != null
                                                        ? document.datetime().toLocalDate()
                                                        : LocalDate.EPOCH,
                                                document.thumbnail()))
                        .toList();

        Meta meta = dto.meta();
        PageInfo pageInfo = new PageInfo(meta.is_end(), meta.pageable_count(), meta.total_count());

        return new SearchBookResponse(books, pageInfo);
    }

    public record Book(String title, List<String> authors, LocalDate publishedAt, String thumbnail) {}

    public record PageInfo(boolean isEnd, int pageableCount, int totalCount) {}
}
