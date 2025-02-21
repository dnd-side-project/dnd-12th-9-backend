package com.dnd.sbooky.api.book.request;

import com.dnd.sbooky.api.common.Enum;
import com.dnd.sbooky.core.book.ReadStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Schema(description = "책 등록 요청 DTO")
public record RegisterBookRequest(
        // @formatter:off
        @Schema(description = "책 제목") @NotBlank String title,
        @Schema(description = "저자") @NotBlank String author,
        @Schema(description = "출판일") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate publishedAt,
        @Schema(
                        description = "읽은 상태",
                        allowableValues = {"WANT_TO_READ", "READING", "COMPLETED"})
                @Enum(enumClass = ReadStatus.class)
                String readStatus,
        @Schema(description = "썸네일 URL")
                @Pattern(regexp = "^$|^(http|https)://.*", message = "http 또는 https로 시작하는 URL을 입력해주세요.")
                String thumbnailUrl) {

    // @formatter:on
}
