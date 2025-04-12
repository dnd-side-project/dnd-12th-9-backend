package com.dnd.sbooky.clients.kakao;

import feign.RequestInterceptor;
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
}
