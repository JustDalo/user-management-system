package com.dalo.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggableAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @After(value = "@within(com.dalo.spring.annotation.Loggable) || @annotation(com.dalo.spring.annotation.Loggable)")
    public void logMethodSetupInfo(JoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().toString();
        String methodName = joinPoint.getSignature().getName();
        String params = joinPoint.getTarget().toString();
        logger.info("target class = " + className);
        logger.info("target method = " + methodName);
        logger.info("method args = " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(
        value = "@within(com.dalo.spring.annotation.Loggable) || @annotation(com.dalo.spring.annotation.Loggable)",
        returning = "result")
    public void logMethodReturnValue(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info("result value = " + result);
    }
}
