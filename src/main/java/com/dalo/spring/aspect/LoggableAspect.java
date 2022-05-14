package com.dalo.spring.aspect;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class LoggableAspect {
    @After(value = "@within(com.dalo.spring.annotation.Loggable) || @annotation(com.dalo.spring.annotation.Loggable)")
    public void logMethodSetupInfo(JoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().toString();
        String methodName = joinPoint.getSignature().getName();
        String params = joinPoint.getTarget().toString();
        log.info("target class = " + className);
        log.info("target method = " + methodName);
        log.info("method args = " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(
        value = "@within(com.dalo.spring.annotation.Loggable) || @annotation(com.dalo.spring.annotation.Loggable)",
        returning = "result")
    public void logMethodReturnValue(JoinPoint joinPoint, Object result) throws Throwable {
        log.info("result value = " + result);
    }
}
