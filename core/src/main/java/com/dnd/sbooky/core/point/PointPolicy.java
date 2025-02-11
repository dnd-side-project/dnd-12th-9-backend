package com.dnd.sbooky.core.point;

import lombok.Getter;

@Getter
public enum PointPolicy {

    // [100번대] 책 관련 포인트 정책
    REGISTER_BOOK(100, 10, 0, "책 등록"),
    COMPLETE_BOOK(101, 50, 0, "책 완독"),

    // [200번대] 미션 관련 포인트 정책
    SHARE_BOOK_CARD(200, 50, 0, "독서 카드 공유"),

    // [500번대] 아이템 관련 포인트 정책
    DRAW_ITEM(500, 0, -100, "아이템 뽑기"),
    ;

    private final Integer id;
    private final int plus;
    private final int minus;
    private final String description;

    PointPolicy(int id, Integer plus, Integer minus, String description) {
        this.id = id;
        this.plus = plus;
        this.minus = minus;
        this.description = description;
    }

    public int calculate(int current) {

        int result = current + plus + minus;
        if (result < 0) {
            // todo: 보유 금액 관련 커스텀 예외 필요
            throw new IllegalStateException("보유 금액이 부족합니다.");
        }

        return result;
    }
}
