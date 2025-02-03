package com.dnd.sbooky.core.book.dto;

import com.dnd.sbooky.core.book.ReadStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record FindBookDetailsDTO(
        Long memberBookId,
        String title,
        String author,
        String thumbnailUrl,
        ReadStatus readStatus,
        LocalDate publishedAt,
        LocalDateTime createdAt,
        LocalDate completedAt) {}
