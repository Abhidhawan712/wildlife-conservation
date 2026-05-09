package com.wildlife.conservation.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

    @Around("execution(* com.wildlife.conservation.service.*.*(..))")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsed = System.currentTimeMillis() - start;

        logger.info("[PERFORMANCE] {}.{} executed in {} ms",
            joinPoint.getTarget().getClass().getSimpleName(),
            joinPoint.getSignature().getName(),
            elapsed);

        if (elapsed > 1000) {
            logger.warn("[SLOW METHOD] {}.{} took over 1 second!",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName());
        }

        return result;
    }
}