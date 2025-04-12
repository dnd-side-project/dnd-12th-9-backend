package com.dnd.sbooky.clients.api;

import com.dnd.sbooky.clients.api.response.SearchBookResponseDTO;

public interface BookSearchAdapter {

    SearchBookResponseDTO search(String query, int page);
}
