package org.example.repository;

import org.springframework.stereotype.Component;
import org.testcontainers.containers.MySQLContainer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class MySQLContainerTest extends MySQLContainer<MySQLContainerTest> {
    public MySQLContainerTest() {
        super(IMAGE_VERSION);
    }

    private final static String IMAGE_VERSION = "mysql:8";

    @PostConstruct
    public void init() {
        withInitScript("schema.sql");
        start();
    }

    @PreDestroy
    public void destroy() {
        stop();
    }
}