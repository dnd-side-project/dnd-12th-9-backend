package com.dnd.sbooky.clients.kakao;

import com.dnd.sbooky.clients.config.KakaoProperties;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class KakaoFeignClientConfig {

    private final KakaoProperties kakaoProperties;

    /**
     * Kakao API 호출 시 Authorization Header 추가하는 Interceptor
     */
    @Bean
    public RequestInterceptor authorizationInterceptor() {
        return requestTemplate ->
                requestTemplate.header("Authorization", "KakaoAK " + kakaoProperties.getAuthorization());
    }

    /**
     * Kakao API 호출 시 발생하는 에러 처리
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new KakaoErrorDecoder();
    }
}
