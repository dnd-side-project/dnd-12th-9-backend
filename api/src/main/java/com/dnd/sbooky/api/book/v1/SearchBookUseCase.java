package com.dnd.sbooky.api.book.v1;

import com.dnd.sbooky.api.book.v1.response.SearchBookResponse;
import com.dnd.sbooky.api.support.cache.CustomCacheManager;
import com.dnd.sbooky.clients.api.BookSearchAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchBookUseCase {

    private final BookSearchAdapter bookSearchAdapter;
    private final CustomCacheManager<SearchBookResponse> cacheManager;

    public SearchBookResponse search(String query, int page) {

        String cacheKey = query + ":" + page;

        return cacheManager
                .getFromCache(cacheKey)
                .map(
                        response -> {
                            log.debug("[Cache hit] key: {}", cacheKey);
                            cacheManager.incrementCount(cacheKey);
                            return response;
                        })
                .orElseGet(
                        () -> {
                            log.debug("[Cache miss] key: {}", cacheKey);
                            SearchBookResponse response =
                                    SearchBookResponse.from(bookSearchAdapter.search(query, page));

                            if (isCacheable(response)) {
                                cacheManager.addToCache(cacheKey, response);
                                log.debug("[Cache add] key: {}", cacheKey);
                            }
                            return response;
                        });
    }

    private boolean isCacheable(SearchBookResponse response) {
        return response.books() != null && !response.books().isEmpty();
    }
}
