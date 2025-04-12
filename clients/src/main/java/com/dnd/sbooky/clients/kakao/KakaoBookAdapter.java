package com.dnd.sbooky.clients.kakao;

import com.dnd.sbooky.clients.api.BookSearchAdapter;
import com.dnd.sbooky.clients.api.response.SearchBookResponseDTO;
import com.dnd.sbooky.clients.kakao.response.KakaoSearchBookResponseDTO;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoBookAdapter implements BookSearchAdapter {

    // spotless:off
    private final KakaoApiClient kakaoApiClient;

    private static final int DEFAULT_SIZE = 10;
    private static final String DEFAULT_SORT = "accuracy";


    @Override
    public SearchBookResponseDTO search(String query, int page) {
        KakaoSearchBookResponseDTO response = kakaoApiClient.searchBooks(
                query,
                page,
                DEFAULT_SIZE,
                DEFAULT_SORT);

        return convertResponse(response, page);
    }

    private SearchBookResponseDTO convertResponse(
            KakaoSearchBookResponseDTO dto, int page) {

        return new SearchBookResponseDTO(
                convertBooks(dto.documents()),
                convertPageInfo(dto.meta(), page));
    }

    private List<SearchBookResponseDTO.Book> convertBooks(
            List<KakaoSearchBookResponseDTO.Document> documents) {
        return documents.stream()
                        .map(document -> new SearchBookResponseDTO.Book(
                                document.title(),
                                convertAuthor(document.authors()),
                                convertPublishedDate(document.datetime()),
                                document.extractThumbnailFileName()))
                        .toList();
    }

    private SearchBookResponseDTO.PageInfo convertPageInfo(
            KakaoSearchBookResponseDTO.Meta meta, int currentPage) {
        return new SearchBookResponseDTO.PageInfo(
                meta.is_end(),
                meta.pageable_count(),
                meta.total_count(),
                currentPage
        );
    }

    private String convertAuthor(List<String> authors) {
        return authors.isEmpty() ? "작자 미상" : authors.get(0);
    }

    private LocalDate convertPublishedDate(OffsetDateTime publishedDate) {
        return publishedDate == null ? LocalDate.EPOCH : publishedDate.toLocalDate();
    }

    // spotless:on
}
