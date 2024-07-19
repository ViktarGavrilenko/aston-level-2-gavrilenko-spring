package org.example.controlers.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.controlers.dto.BuyerDTOTest.getTempListInteger;
import static org.junit.jupiter.api.Assertions.*;

class OrderDTOTest {
    private final int id = 1;
    private final int number = 1111;
    private final List<Integer> items = getTempListInteger();
    private final int idBuyer = 1;


    @Test
    void getId() {
        OrderDTO order = new OrderDTO(id, number, items, idBuyer);
        assertEquals(id, order.getId());
    }

    @Test
    void setId() {
        OrderDTO order = new OrderDTO();
        order.setId(id);
        assertEquals(id, order.getId());
    }

    @Test
    void getNumber() {
        OrderDTO order = new OrderDTO(id, number, items, idBuyer);
        assertEquals(number, order.getNumber());
    }

    @Test
    void setNumber() {
        OrderDTO order = new OrderDTO();
        order.setNumber(number);
        assertEquals(number, order.getNumber());
    }

    @Test
    void getItems() {
        OrderDTO order = new OrderDTO(id, number, items, idBuyer);
        assertEquals(items, order.getItems());
    }

    @Test
    void setItems() {
        OrderDTO order = new OrderDTO();
        order.setItems(items);
        assertEquals(items, order.getItems());
    }
}