package org.example.repository.mapper;

import org.example.models.Order;
import org.example.repository.ItemRepository;
import org.example.repository.impl.ItemRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderResultSetMapper implements RowMapper<Order> {
    private final ItemRepository itemRepository;

    @Autowired
    public OrderResultSetMapper(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        int number = rs.getInt("number");
        return new Order(id, number, new ArrayList<>());
        //return new Order(id, number, itemRepository.getListItemsInOrderById(id));
    }
}