package com.dalo.spring.annotation.impl;

import com.dalo.spring.annotation.Metric;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class MetricAnnotationProcessor {
    private final Counter counter;

    public MetricAnnotationProcessor(MeterRegistry registry) {
        this.counter = registry.counter("received.messages");
    }

    public void process(Method method) {
        Metric annotation = method.getAnnotation(Metric.class);
        String value = annotation.value();
        this.counter.increment();
    }
}
