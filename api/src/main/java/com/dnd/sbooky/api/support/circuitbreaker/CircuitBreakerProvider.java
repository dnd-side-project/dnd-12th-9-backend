package com.dnd.sbooky.api.support.circuitbreaker;

public class CircuitBreakerProvider {

    private CircuitBreakerProvider() {
        throw new IllegalStateException("Utility class");
    }

    public static final String REDIS_CACHE = "REDIS_CACHE";
    public static final String RATE_LIMITER = "RATE_LIMITER";
}
