package com.dnd.sbooky.api.book.v1;

import com.dnd.sbooky.api.book.v1.response.SearchBookResponse;
import com.dnd.sbooky.clients.api.BookSearchAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchBookUseCase {

    private final BookSearchAdapter bookSearchAdapter;

    public SearchBookResponse search(String query, int page) {

        return SearchBookResponse.from(bookSearchAdapter.search(query, page));
    }
}
