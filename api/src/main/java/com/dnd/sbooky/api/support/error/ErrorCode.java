package com.dnd.sbooky.api.support.error;

public enum ErrorCode {

    // TODO: 에러 코드 추가 필요 (Domain + HTTP Status + [Number])

    // Server Error
    E500,
    E400_1,
    E400_2,
    E403,
    E404,

    // Member Error
    MEMBER_404,

    // Book Error
    BOOK_400_1,
    BOOK_403,
    BOOK_404,

    // Security Error
    SECURITY_401_2,
    SECURITY_401_3,
    SECURITY_404_1,
    SECURITY_401_1,

    // Item Error
    ITEM_404,
    ITEM_404_2,

    // Evaluation Error
    EVALUATION_404_1,;
}
