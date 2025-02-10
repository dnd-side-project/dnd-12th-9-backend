package com.dnd.sbooky.clients.kakao.response;

import java.time.OffsetDateTime;
import java.util.List;

public record SearchBookResponseDTO(List<Document> documents, Meta meta) {

    public record Document(
            String title,
            String contents,
            String isbn,
            String publisher,
            List<String> authors,
            String thumbnail,
            OffsetDateTime datetime) {}

    public record Meta(boolean is_end, int pageable_count, int total_count) {}
}
