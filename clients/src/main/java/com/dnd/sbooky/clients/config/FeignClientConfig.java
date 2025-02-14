package com.dnd.sbooky.clients.config;

import feign.Request;
import java.time.Duration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.dnd.sbooky.clients")
class FeignClientConfig {

    private static final long CONNECT_TIMEOUT_MILLIS = 5000L;
    private static final long READ_TIMEOUT_MILLIS = 5000L;

    /**
     * Timeout Setting
     */
    @Bean
    Request.Options feignOptions() {
        return new Request.Options(
                Duration.ofMillis(CONNECT_TIMEOUT_MILLIS),
                Duration.ofMillis(READ_TIMEOUT_MILLIS),
                true
        );
    }
}