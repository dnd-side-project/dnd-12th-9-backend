package com.dnd.sbooky.clients.kakao.response;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.List;

public record KakaoSearchBookDTO(List<Document> documents, Meta meta) {

    public record Document(
            String title,
            String contents,
            String isbn,
            String publisher,
            List<String> authors,
            String thumbnail,
            OffsetDateTime datetime,
            int price,
            int sale_price,
            String status,
            String url,
            List<String> translators) {

        /**
         * 썸네일 이미지 추출 코드
         */
        public String extractThumbnailFileName() {

            if (thumbnail.isBlank()) {
                return "";
            }

            try {
                String fileName = thumbnail.substring(thumbnail.indexOf("fname=") + 6);
                return URLDecoder.decode(fileName, StandardCharsets.UTF_8);
            } catch (Exception e) {
                return "";
            }
        }
    }

    public record Meta(boolean is_end, int pageable_count, int total_count) {}
}
