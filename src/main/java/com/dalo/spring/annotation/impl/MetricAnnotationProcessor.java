package com.dalo.spring.annotation.impl;

import com.dalo.spring.annotation.AnnotationProcessor;
import com.dalo.spring.annotation.Metric;
import io.micrometer.core.instrument.Counter;
import org.springframework.boot.actuate.metrics.*;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class MetricAnnotationProcessor implements AnnotationProcessor {
    private Counter counter;
    private MeterRegistry registry;

    public MetricAnnotationProcessor(MeterRegistry registry) {
        this.registry = registry;
    }

    public void initCounter(String value) {
        this.counter = registry.counter("app.usermanagement." + value);
    }

    @Override
    public void process(Method method) {
        this.counter.increment();
    }
}
