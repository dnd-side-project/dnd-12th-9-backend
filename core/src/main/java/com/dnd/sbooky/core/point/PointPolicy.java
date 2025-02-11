package com.dnd.sbooky.core.point;

import lombok.Getter;

@Getter
public enum PointPolicy {

    // [100번대] 책 관련 포인트 정책
    REGISTER_BOOK(100, 10, "책 등록"),
    COMPLETE_BOOK(101, 50, "책 완독"),

    // [200번대] 미션 관련 포인트 정책
    SHARE_BOOK_CARD(200, 50, "독서 카드 공유"),

    // [500번대] 아이템 관련 포인트 정책
    DRAW_ITEM(500, -100, "아이템 뽑기"),
    ;

    private final Integer id;
    private final int point;
    private final String description;

    PointPolicy(Integer id, int point, String description) {
        this.id = id;
        this.point = point;
        this.description = description;
    }
}
