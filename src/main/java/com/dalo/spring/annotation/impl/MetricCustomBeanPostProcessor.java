package com.dalo.spring.annotation.impl;

import com.dalo.spring.annotation.AnnotationProcessor;
import com.dalo.spring.annotation.Metric;
import io.micrometer.core.instrument.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class MetricCustomBeanPostProcessor implements BeanPostProcessor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AnnotationProcessor metricAnnotationProcessor;
    private Map<String, Class> map = new HashMap<>();

    public MetricCustomBeanPostProcessor(MetricAnnotationProcessor metricAnnotationProcessor) {
        this.metricAnnotationProcessor = metricAnnotationProcessor;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        for (Method method : beanClass.getMethods()) {
            if (method.isAnnotationPresent(Metric.class)) {
                map.put(beanName, beanClass);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.isAnnotationPresent(Metric.class)) {
                            metricAnnotationProcessor.initCounter(method.getAnnotation(Metric.class).value());
                            metricAnnotationProcessor.process(method);
                            Object retVal = method.invoke(bean, args);
                            return retVal;
                        } else {
                            return method.invoke(bean, args);
                        }
                    }
                });
        }
        return bean;
    }
}
