package com.dalo.spring.utils;

import org.testcontainers.containers.MySQLContainer;

public class LocalhostMySqlContainer extends MySQLContainer<LocalhostMySqlContainer> {
    private static final String IMAGE_VERSION = "mysql:latest";
    private static LocalhostMySqlContainer container;

    private LocalhostMySqlContainer() {
        super(IMAGE_VERSION);
    }

    public static LocalhostMySqlContainer getInstance() {
        if (container == null) {
            container = new LocalhostMySqlContainer();
        }
        return container;
    }
}
