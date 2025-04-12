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
            @RequestParam String query, @RequestParam(defaultValue = "1") int page) {

        return ApiResponse.success(searchBookUseCase.search(query, page));
    }
}
