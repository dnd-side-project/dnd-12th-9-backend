package com.dnd.sbooky.api.book;

import com.dnd.sbooky.clients.book.KakaoApiClient;
import com.dnd.sbooky.clients.book.response.SearchBookResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchBookUseCase {

    private final KakaoApiClient kakaoApiClient;

    public SearchBookResponseDTO searchBook(String query, String sort, int size, int page, String target) {

        log.info("카카오 API 호출 = query: {}", query);
        return kakaoApiClient.searchBooks(query, sort, page, size, target);
    }
}
