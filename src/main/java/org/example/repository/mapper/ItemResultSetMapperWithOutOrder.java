package org.example.repository.mapper;

import org.example.models.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemResultSetMapperWithOutOrder implements RowMapper<Item> {

    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int price = rs.getInt("price");
        return new Item(id, name, price, new ArrayList<>());
    }
}