package com.dnd.sbooky.core.book.dto;

import com.dnd.sbooky.core.book.ReadStatus;

public record MemberBookResponseDTO(
        Long id,
        String title,
        String author,
        String thumbnailUrl,
        ReadStatus readStatus
) {

}
