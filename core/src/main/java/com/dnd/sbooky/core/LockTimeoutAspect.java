package com.dnd.sbooky.core;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LockTimeoutAspect {
    private final LockTimeoutRepository lockTimeoutRepository;

    @Before("@annotation(com.dnd.sbooky.core.LockTimeout)")
    public void setTimeoutBeforeLock(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LockTimeout lockTimeout = methodSignature.getMethod().getAnnotation(LockTimeout.class);
        lockTimeoutRepository.setInnodbLockWaitTimeout(lockTimeout.timeout());
    }
}
