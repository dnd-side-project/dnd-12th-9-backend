package com.dnd.sbooky.api.book.v1;

import com.dnd.sbooky.api.book.v1.response.SearchBookResponse;
import com.dnd.sbooky.api.docs.spec.SearchBookApiSpec;
import com.dnd.sbooky.api.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchBookController implements SearchBookApiSpec {

    private final SearchBookUseCase searchBookUseCase;

    @GetMapping("/books")
    public ApiResponse<SearchBookResponse> searchBook(
            @RequestParam(required = true) String query,
            @RequestParam(defaultValue = "accuracy", required = false) String sort,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(required = false) String target) {

        // todo: 무작위한 검색을 막기 위해 사용자 검증이 필요할까?

        return ApiResponse.success(searchBookUseCase.search(query, sort, size, page, target));
    }
}
