package org.example.repository.impl;

import org.junit.BeforeClass;
import org.springframework.context.annotation.PropertySource;
import org.testcontainers.containers.MySQLContainer;

@PropertySource("classpath:database.properties")
class TestContainerConfig {

    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>(
            "mysql:8"
    ).withDatabaseName("task2_aston")
            .withUsername("root")
            .withPassword("")
            .withInitScript("schema.sql");

    @BeforeClass
    public static void beforeAll() {
        mySQLContainer.start();
    }
}