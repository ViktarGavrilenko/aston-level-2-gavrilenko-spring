package org.example.repository.mapper;

import org.example.models.Item;
import org.example.models.Order;
import org.example.repository.impl.ItemRepositoryImpl;
import org.example.repository.impl.OrderRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemResultSetMapper implements RowMapper<Item> {

    private static OrderRepositoryImpl orderRepository;

    @Autowired
    public ItemResultSetMapper(OrderRepositoryImpl orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int price = rs.getInt("price");
        return new Item(id, name, price, orderRepository.getListOrderByIdItem(id));
    }
}