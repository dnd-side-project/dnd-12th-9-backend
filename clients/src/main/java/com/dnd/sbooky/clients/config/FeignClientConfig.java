package com.dnd.sbooky.clients.config;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import java.time.Duration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableFeignClients(basePackages = "com.dnd.sbooky.clients")
class FeignClientConfig {

    private static final long CONNECT_TIMEOUT_MILLIS = 5000L;
    private static final long READ_TIMEOUT_MILLIS = 5000L;

    private static final long RETRY_PERIOD = 100L;
    private static final long RETRY_MAX_PERIOD = 2000L;
    private static final int RETRY_MAX_ATTEMPTS = 3;

    /**
     * HTTP 요청에 대한 Timeout 설정
     * <p>
     *
     * <ul>
     *  <li>Connection Timeout: {@value CONNECT_TIMEOUT_MILLIS}ms - 서버와의 연결 시도 제한 시간
     *  <li>Read Timeout: {@value READ_TIMEOUT_MILLIS}ms - 서버로부터 응답 대기 제한 시간
     * </ul>
     *
     * @return Request.Options 타임아웃 설정 객체
     */
    @Bean
    Request.Options feignOptions() {
        return new Request.Options(
                Duration.ofMillis(CONNECT_TIMEOUT_MILLIS), Duration.ofMillis(READ_TIMEOUT_MILLIS), true);
    }

    /**
     * HTTP 요청 실패 시 재시도 설정
     * <p>
     *
     * <ul>
     *  <li>초기 재시도 간격: {@value RETRY_PERIOD}ms
     *  <li>최대 재시도 간격: {@value RETRY_MAX_PERIOD}ms
     *  <li>최대 재시도 횟수: {@value RETRY_MAX_ATTEMPTS}회
     * </ul>
     *
     * <p>
     * 재시도 간격은 {@value RETRY_PERIOD}ms 에서 시작하여 점차 증가하며,
     * 최대 {@value RETRY_MAX_PERIOD}ms를 초과하지 않습니다.
     *
     * @return Retryer.Default 재시도 설정 객체
     */
    @Bean
    Retryer.Default feignRetryer() {
        return new Retryer.Default(RETRY_PERIOD, RETRY_MAX_PERIOD, RETRY_MAX_ATTEMPTS);
    }

    /**
     * FeignClient 로깅 설정
     * <p>
     * 운영 환경(prod)을 제외한 모든 환경에서 기본(BASIC) 레벨의 로깅을 활성화합니다.
     *
     * @return Logger.Level 로깅 레벨
     */
    @Profile("!prod")
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
}
