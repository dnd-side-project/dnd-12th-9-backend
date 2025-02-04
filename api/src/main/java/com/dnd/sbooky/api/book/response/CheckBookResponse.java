package com.dnd.sbooky.api.book.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "책 확인 응답 DTO")
public record CheckBookResponse(@Schema(description = "책 존재 여부") boolean exist) {}
