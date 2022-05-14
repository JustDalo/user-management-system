package com.dalo.spring.annotation;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public interface AnnotationProcessor {
    void initCounter(String value);
    void process(Method method);
}
