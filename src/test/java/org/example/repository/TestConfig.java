package org.example.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "org.example")
public class TestConfig {
    @Bean
    public DataSource dataSource(MySQLContainerTest mySQLContainerTest) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(mySQLContainerTest.getDriverClassName());
        dataSource.setUrl(mySQLContainerTest.getJdbcUrl());
        dataSource.setUsername(mySQLContainerTest.getUsername());
        dataSource.setPassword(mySQLContainerTest.getPassword());

        return dataSource;
    }
}
