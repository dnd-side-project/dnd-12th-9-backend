package com.dnd.sbooky.book.request;

import com.dnd.sbooky.book.ReadStatus;
import com.dnd.sbooky.common.Enum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Schema(description = "책 등록 요청 DTO")
public record RegisterBookRequest(

        @Schema(description = "책 제목")
        @NotBlank String title,

        @Schema(description = "저자")
        @NotBlank String author,

        @Schema(description = "출판일")
        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate publishedAt,

        @Schema(description = "읽은 상태", allowableValues = {"WANT_TO_READ", "READING", "COMPLETED"})
        @Enum(enumClass = ReadStatus.class) String readStatus
) {
}
