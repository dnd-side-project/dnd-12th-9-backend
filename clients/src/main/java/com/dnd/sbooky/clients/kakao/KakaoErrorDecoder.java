package com.dnd.sbooky.clients.kakao;


import feign.Response;
import feign.codec.ErrorDecoder;

public class KakaoErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        String message = getErrorMessage(status);
        return new KakaoApiException(message, status);
    }

    private String getErrorMessage(int status) {
        return switch (status) {
            case 400 -> "잘못된 요청입니다. 파라미터를 확인해주세요.";
            case 401 -> "인증에 실패했습니다. API 키를 확인해주세요.";
            case 403 -> "접근 권한이 없습니다.";
            case 429 -> "API 호출 제한(쿼터)을 초과했습니다.";
            case 500 -> "카카오 서버 내부에서 에러가 발생했습니다.";
            case 502 -> "카카오 서버와 통신이 원활하지 않습니다.";
            case 503 -> "카카오 서비스가 일시적으로 중단되었습니다.";
            default -> "Kakao API 호출 중 에러가 발생했습니다: " + status;
        };
    }
}
