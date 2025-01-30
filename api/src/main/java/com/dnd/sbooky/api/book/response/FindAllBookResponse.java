package com.dnd.sbooky.api.book.response;


import com.dnd.sbooky.core.book.dto.MemberBookResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "모든 책 조회 응답 DTO")
public record FindAllBookResponse(

        @Schema(description = "책 목록")
        List<MemberBookResponseDTO> bookList
) {
    public static FindAllBookResponse of(List<MemberBookResponseDTO> response) {
        return new FindAllBookResponse(response);
    }
}
