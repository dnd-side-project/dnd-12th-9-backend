package com.dnd.book.book.request;

import com.dnd.book.book.ReadStatus;
import com.dnd.book.common.Enum;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public record RegisterBookRequest(
        @NotBlank String title,
        @NotBlank String author,
        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate publishedAt,
        @Enum(enumClass = ReadStatus.class) String readStatus
) {
}
