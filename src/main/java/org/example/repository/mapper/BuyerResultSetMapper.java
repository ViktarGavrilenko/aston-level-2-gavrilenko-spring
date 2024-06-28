package org.example.repository.mapper;

import org.example.models.Buyer;
import org.example.repository.impl.OrderRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuyerResultSetMapper implements RowMapper<Buyer> {
    private static OrderRepositoryImpl orderRepository;

    @Autowired
    public BuyerResultSetMapper(OrderRepositoryImpl orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Buyer mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Buyer(id, name, orderRepository.getListOfBuyerOrdersById(id));
    }
}