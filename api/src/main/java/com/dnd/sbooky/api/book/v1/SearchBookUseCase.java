package com.dnd.sbooky.api.book.v1;

import com.dnd.sbooky.api.book.v1.response.SearchBookResponse;
import com.dnd.sbooky.api.support.RedisKey;
import com.dnd.sbooky.api.support.cache.PerRedisCacheManager;
import com.dnd.sbooky.api.support.circuitbreaker.CircuitBreakerProvider;
import com.dnd.sbooky.clients.api.BookSearchAdapter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchBookUseCase {

    private final BookSearchAdapter bookSearchAdapter;
    private final PerRedisCacheManager cacheManager;

    @CircuitBreaker(name = CircuitBreakerProvider.REDIS_CACHE, fallbackMethod = "searchWithoutCache")
    public SearchBookResponse search(String query, int page) {

        String cacheKey = RedisKey.getBookCacheKey(query, page);

        return cacheManager.getOrLoad(
                cacheKey,
                SearchBookResponse.class,
                () -> SearchBookResponse.from(bookSearchAdapter.search(query, page)));
    }

    public SearchBookResponse searchWithoutCache(String query, int page, Throwable e) {
        log.error("Cache unavailable, fetching data without cache. Error: {}", e.getMessage());
        return SearchBookResponse.from(bookSearchAdapter.search(query, page));
    }
}
