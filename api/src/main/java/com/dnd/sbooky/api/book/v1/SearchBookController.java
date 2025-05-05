package com.dnd.sbooky.api.book.v1;

import com.dnd.sbooky.api.book.v1.response.SearchBookResponse;
import com.dnd.sbooky.api.docs.spec.SearchBookApiSpec;
import com.dnd.sbooky.api.support.response.ApiResponse;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchBookController implements SearchBookApiSpec {

    private static final Pattern WHITE_SPACE = Pattern.compile("\\s+");
    private static final Pattern NON_ALPHANUMERIC = Pattern.compile("[^가-힣a-z0-9]");

    private final SearchBookUseCase searchBookUseCase;

    @GetMapping("/books")
    public ApiResponse<SearchBookResponse> searchBook(
            @RequestParam String query, @RequestParam(defaultValue = "1") int page) {

        String normalizeQuery = normalizeQuery(query);
        return ApiResponse.success(searchBookUseCase.search(normalizeQuery, page));
    }

    /**
     * 검색어를 아래와 같은 순서로 정규화합니다.
     * <ol>
     *     <li>공백 제거</li>
     *     <li>알파벳 소문자 변환</li>
     *     <li>특수 문자 제거</li>
     * </oi>
     *
     * @param query 정규화할 검색어
     * @return 정규화된 검색어
     */
    private String normalizeQuery(String query) {

        String normalizedQuery = WHITE_SPACE.matcher(query).replaceAll("");
        normalizedQuery = normalizedQuery.toLowerCase();
        normalizedQuery = NON_ALPHANUMERIC.matcher(normalizedQuery).replaceAll("");

        return normalizedQuery;
    }
}
