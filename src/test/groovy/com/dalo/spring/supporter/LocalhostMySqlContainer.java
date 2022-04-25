package com.dalo.spring.supporter;

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

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
