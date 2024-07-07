package org.example.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.springframework.test.context.transaction.TestTransaction.start;

public class BaseTest {
    private final JdbcTemplate jdbcTemplate;
    public static final String DELETE_ALL_BUYER = "DELETE FROM buyers WHERE 1;";
    public static final String DELETE_ALL_ITEM = "DELETE FROM items WHERE 1;";
    public static final String DELETE_ALL_ORDER = "DELETE FROM orders WHERE 1;";
    public static final String DELETE_ALL_BUYER_ORDER = "DELETE FROM buyer_order WHERE 1;";
    public static final String DELETE_ALL_ORDER_ITEMS = "DELETE FROM order_items WHERE 1;";

    @Autowired
    public BaseTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.update(DELETE_ALL_BUYER);
        jdbcTemplate.update(DELETE_ALL_ITEM);
        jdbcTemplate.update(DELETE_ALL_ORDER);
        jdbcTemplate.update(DELETE_ALL_BUYER_ORDER);
        jdbcTemplate.update(DELETE_ALL_ORDER_ITEMS);
    }
}
