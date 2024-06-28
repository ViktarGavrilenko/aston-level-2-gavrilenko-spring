package org.example.repository.mapper;

import org.example.models.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderResultMapperWithOutItems implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        int number = rs.getInt("number");
        return new Order(id, number, new ArrayList<>());
    }
}