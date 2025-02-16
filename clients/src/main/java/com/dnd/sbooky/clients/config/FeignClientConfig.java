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
     * Timeout Setting
     */
    @Bean
    Request.Options feignOptions() {
        return new Request.Options(
                Duration.ofMillis(CONNECT_TIMEOUT_MILLIS), Duration.ofMillis(READ_TIMEOUT_MILLIS), true);
    }

    /**
     * Retry Setting
     */
    @Bean
    Retryer.Default feignRetryer() {
        return new Retryer.Default(RETRY_PERIOD, RETRY_MAX_PERIOD, RETRY_MAX_ATTEMPTS);
    }

    /**
     * Logging Setting
     */
    @Profile("!prod")
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
}
