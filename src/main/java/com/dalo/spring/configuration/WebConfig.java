package com.dalo.spring.configuration;

import com.dalo.spring.annotation.impl.MetricCustomBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.dalo.spring.**")
public class WebConfig {
}
