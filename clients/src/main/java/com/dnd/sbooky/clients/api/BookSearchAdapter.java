package com.dnd.sbooky.clients.api;

import com.dnd.sbooky.clients.api.response.SearchBookDTO;

public interface BookSearchAdapter {

    SearchBookDTO search(String query, int page);
}
