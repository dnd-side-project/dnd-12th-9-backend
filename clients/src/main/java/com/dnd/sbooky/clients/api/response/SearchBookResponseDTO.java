package com.dnd.sbooky.clients.api.response;

import java.time.LocalDate;
import java.util.List;

public record SearchBookResponseDTO(List<Book> books, PageInfo pageInfo) {

    public record Book(String title, String author, LocalDate publishedAt, String thumbnail) {}

    public record PageInfo(boolean isEnd, int pageableCount, int totalCount, int page) {}
}
