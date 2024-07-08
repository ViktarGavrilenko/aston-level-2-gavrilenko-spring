package org.example.controlers.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.controlers.dto.BuyerDTOTest.getTempListInteger;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemDTOTest {
    private final int id = 1;
    private final String name = "someName";
    private final int price = 10;
    public List<Integer> orders = getTempListInteger();

    @Test
    void getId() {
        ItemDTO item = new ItemDTO(id, name, price, orders);
        assertEquals(id, item.getId());
    }

    @Test
    void setId() {
        ItemDTO item = new ItemDTO();
        item.setId(id);
        assertEquals(id, item.getId());
    }

    @Test
    void getName() {
        ItemDTO item = new ItemDTO(id, name, price, orders);
        assertEquals(name, item.getName());
    }

    @Test
    void setName() {
        ItemDTO item = new ItemDTO();
        item.setName(name);
        assertEquals(name, item.getName());
    }

    @Test
    void getPrice() {
        ItemDTO item = new ItemDTO(id, name, price, orders);
        assertEquals(price, item.getPrice());
    }

    @Test
    void setPrice() {
        ItemDTO item = new ItemDTO();
        item.setPrice(price);
        assertEquals(price, item.getPrice());
    }

    @Test
    void getOrders() {
        ItemDTO item = new ItemDTO(id, name, price, orders);
        assertEquals(orders, item.getOrders());
    }

    @Test
    void setOrders() {
        ItemDTO item = new ItemDTO();
        item.setOrders(orders);
        assertEquals(orders, item.getOrders());
    }
}