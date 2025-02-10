package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.response.SearchBookResponse;
import com.dnd.sbooky.clients.kakao.KakaoApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchBookUseCase {

    private final KakaoApiClient kakaoApiClient;

    public SearchBookResponse search(String query, String sort, int size, int page, String target) {
        return SearchBookResponse.from(kakaoApiClient.searchBooks(query, sort, page, size, target));
    }
}
