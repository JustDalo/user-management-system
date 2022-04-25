package com.dalo.spring.utils

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.MySQLContainer

class TestPropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private MySQLContainer mySqlContainer = LocalhostMySqlContainer.getInstance()

    @Override
    void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
               "spring.datasource.url=${mySqlContainer.getJdbcUrl()}",
                "spring.datasource.username=${mySqlContainer.getUsername()}",
                "spring.datasource.password=${mySqlContainer.getPassword()}"
        ).applyTo(applicationContext.environment)
    }
}
