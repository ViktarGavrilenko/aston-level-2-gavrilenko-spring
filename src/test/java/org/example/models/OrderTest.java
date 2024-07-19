package org.example.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {
    private final int id = 1;
    private final int number = 1111;
    private final List<Item> items = getTempItems();
    private final Buyer buyer = new Buyer();

    @Test
    void getId() {
        Order order = new Order(id, number, items, buyer);
        assertEquals(id, order.getId());
    }

    @Test
    void setId() {
        Order order = new Order();
        order.setId(id);
        assertEquals(id, order.getId());
    }

    @Test
    void getNumber() {
        Order order = new Order(id, number, items, buyer);
        assertEquals(number, order.getNumber());
    }

    @Test
    void setNumber() {
        Order order = new Order();
        order.setNumber(number);
        assertEquals(number, order.getNumber());
    }

    @Test
    void getItems() {
        Order order = new Order(id, number, items, buyer);
        assertEquals(items, order.getItems());
    }

    @Test
    void setItems() {
        Order order = new Order();
        order.setItems(items);
        assertEquals(items, order.getItems());
    }

    static List<Item> getTempItems() {
        int size = 5;
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Item item = new Item(i, "SomeItem" + i, i * 2, new ArrayList<>());
            items.add(item);
        }
        return items;
    }
}