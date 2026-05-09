package com.wildlife.conservation.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Log before any service method
    @Before("execution(* com.wildlife.conservation.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("[BEFORE] Method: {} | Args: {}",
            joinPoint.getSignature().getName(),
            Arrays.toString(joinPoint.getArgs()));
    }

    // Log after observation create/update
    @AfterReturning(
        pointcut = "execution(* com.wildlife.conservation.service.ObservationService.createObservation(..)) || " +
                   "execution(* com.wildlife.conservation.service.ObservationService.updateObservation(..))",
        returning = "result"
    )
    public void logObservationSaved(JoinPoint joinPoint, Object result) {
        logger.info("[OBSERVATION SAVED] Method: {} | Result: {}",
            joinPoint.getSignature().getName(), result);
    }

    // Log exceptions in any service
    @AfterThrowing(pointcut = "execution(* com.wildlife.conservation.service.*.*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        logger.error("[EXCEPTION] Method: {} | Exception: {}",
            joinPoint.getSignature().getName(), ex.getMessage());
    }
}