package com.dnd.sbooky.api.book.v1;

import com.dnd.sbooky.api.book.v1.response.SearchBookResponse;
import com.dnd.sbooky.api.support.cache.CustomCacheManager;
import com.dnd.sbooky.clients.api.BookSearchAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SearchBookUseCase {

    private final BookSearchAdapter bookSearchAdapter;
    private final CustomCacheManager<SearchBookResponse> cacheManager;

    public SearchBookUseCase(
            BookSearchAdapter bookSearchAdapter,
            @Qualifier("caffeineCacheManager") CustomCacheManager<SearchBookResponse> cacheManager) {
        this.bookSearchAdapter = bookSearchAdapter;
        this.cacheManager = cacheManager;
    }

    public SearchBookResponse search(String query, int page) {

        String cacheKey = query + ":" + page;

        SearchBookResponse cachedResponse = cacheManager.getFromCache(cacheKey);
        if (cachedResponse != null) {
            return cachedResponse;
        }

        SearchBookResponse response = SearchBookResponse.from(bookSearchAdapter.search(query, page));

        if (isCacheable(response)) {
            cacheManager.addToCache(cacheKey, response);
        }

        return response;
    }

    private boolean isCacheable(SearchBookResponse response) {
        return response.books() != null && !response.books().isEmpty();
    }
}
