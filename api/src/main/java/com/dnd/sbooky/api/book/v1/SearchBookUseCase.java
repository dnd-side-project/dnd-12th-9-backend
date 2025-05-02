package com.dnd.sbooky.api.book.v1;

import com.dnd.sbooky.api.book.v1.response.SearchBookResponse;
import com.dnd.sbooky.api.support.cache.CustomCacheManager;
import com.dnd.sbooky.clients.api.BookSearchAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SearchBookUseCase {

    private final BookSearchAdapter bookSearchAdapter;
    private final CustomCacheManager<SearchBookResponse> cacheManager;

    public SearchBookUseCase(
            BookSearchAdapter bookSearchAdapter,

            @Qualifier(value = "linkedHashMapCacheManager")
            CustomCacheManager<SearchBookResponse> cacheManager) {
        this.bookSearchAdapter = bookSearchAdapter;
        this.cacheManager = cacheManager;
    }

    public SearchBookResponse search(String query, int page) {
        // 1. 캐시에서 검색 결과를 확인한다.
        String cacheKey = query + ":" + page;
        SearchBookResponse cachedResponse = cacheManager.getFromCache(cacheKey);
        if (cachedResponse != null) {
            // 2. 검색 결과가 있을 경우 캐시된 결과를 반환한다.
            log.info("------- Cache Hit -------");
            return cachedResponse;
        }

        // 3. 캐시된 결과가 없을 경우 외부 API를 호출하여 검색 결과를 가져온다.
        SearchBookResponse response = SearchBookResponse.from(bookSearchAdapter.search(query, page));

        // 4. 가져온 검색 결과를 캐시에 저장한다.
        cacheManager.addToCache(cacheKey, response);
        log.info("------- Cache Miss -------");
        return response;
    }
}
