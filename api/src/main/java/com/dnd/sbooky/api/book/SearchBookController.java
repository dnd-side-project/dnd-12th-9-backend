package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.support.response.ApiResponse;
import com.dnd.sbooky.clients.book.response.SearchBookResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchBookController {

    private final SearchBookUseCase searchBookUseCase;

    @GetMapping("/books")
    public ApiResponse<?> searchBook(
            @RequestParam(required = true) String query,
            @RequestParam(defaultValue = "accuracy") String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String target) {
        SearchBookResponseDTO response = searchBookUseCase.searchBook(query, sort, size, page, target);
        return ApiResponse.success(response);
    }
}
