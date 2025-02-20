package com.dnd.sbooky.core.book.dto;

import com.dnd.sbooky.core.book.ReadStatus;
import java.util.List;

public record FindBooksDTO(long totalBookCount, List<FindBookDTO> books) {

    public record FindBookDTO(
            Long id, String title, String author, String thumbnailUrl, ReadStatus readStatus) {}
}
