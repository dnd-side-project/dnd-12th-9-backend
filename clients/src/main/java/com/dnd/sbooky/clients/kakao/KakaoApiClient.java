package com.dnd.sbooky.clients.kakao;

import com.dnd.sbooky.clients.kakao.response.KakaoSearchBookResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "kakao-book-api",
        url = "https://dapi.kakao.com/v3",
        configuration = KakaoFeignClientConfig.class)
public interface KakaoApiClient {

    /**
     * 카카오 책 검색 API
     *
     * @param query  검색어 (필수)
     * @param sort   정렬 방식 (accuracy, recency) - 기본값 accuracy
     * @param page   결과 페이지 번호 (1 ~ 50) - 기본값 1
     * @param size   한 페이지에 보여질 문서의 개수 (1 ~ 50) - 기본값 10
     * @param target 검색 필드 (title, isbn, publisher, person) - 기본값 X
     */
    @GetMapping("/search/book")
    KakaoSearchBookResponseDTO searchBooks(
            @RequestParam(value = "query", required = true) String query,
            @RequestParam(value = "sort", defaultValue = "accuracy", required = false) String sort,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "target", required = false) String target);
}
