package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.request.SearchBookRequest;
import com.dnd.sbooky.api.book.response.SearchBookResponse;
import com.dnd.sbooky.api.docs.spec.SearchBookApiSpec;
import com.dnd.sbooky.api.support.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchBookController implements SearchBookApiSpec {

    private final SearchBookUseCase searchBookUseCase;

    @GetMapping("/books/v1")
    public ApiResponse<SearchBookResponse> searchBook(
            @Valid @ModelAttribute SearchBookRequest request) {

        // todo: 무작위한 검색을 막기 위해 사용자 검증이 필요할까?
        SearchBookResponse response = searchBookUseCase.searchBook(request);
        return ApiResponse.success(response);
    }

    @GetMapping("/books/v2")
    public ApiResponse<SearchBookResponse> searchBookV2(
            @RequestParam(required = true) String query,
            @RequestParam(defaultValue = "accuracy") String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String target) {

        return ApiResponse.success(searchBookUseCase.searchBookV2(query, sort, size, page, target));
    }
}
