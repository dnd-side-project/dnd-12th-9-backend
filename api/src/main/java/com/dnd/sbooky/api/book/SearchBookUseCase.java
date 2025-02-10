package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.request.SearchBookRequest;
import com.dnd.sbooky.api.book.response.SearchBookResponse;
import com.dnd.sbooky.clients.kakao.KakaoApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchBookUseCase {

    private final KakaoApiClient kakaoApiClient;

    public SearchBookResponse searchBook(SearchBookRequest request) {
        return SearchBookResponse.from(
                kakaoApiClient.searchBooks(
                        request.query(), request.sort(), request.page(), request.size(), request.target()));
    }

    public SearchBookResponse searchBookV2(
            String query, String sort, int size, int page, String target) {
        return SearchBookResponse.from(kakaoApiClient.searchBooks(query, sort, page, size, target));
    }
}
