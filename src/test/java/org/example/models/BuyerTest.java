package org.example.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuyerTest {
    private final int id = 1;
    private final String name = "someName";

    @Test
    void getId() {
        Buyer buyer = new Buyer(id, name, new ArrayList<>());
        assertEquals(id, buyer.getId());
    }

    @Test
    void setId() {
        Buyer buyer = new Buyer();
        buyer.setId(id);
        assertEquals(id, buyer.getId());
    }

    @Test
    void getName() {
        Buyer buyer = new Buyer(id, name, new ArrayList<>());
        assertEquals(name, buyer.getName());
    }

    @Test
    void setName() {
        Buyer buyer = new Buyer();
        buyer.setName(name);
        assertEquals(name, buyer.getName());
    }

    @Test
    void getOrders() {
        Buyer buyer = new Buyer(id, name, getTempOrders());
        assertEquals(getTempOrders(), buyer.getOrders());
    }

    @Test
    void setOrders() {
        Buyer buyer = new Buyer();
        buyer.setOrders(getTempOrders());
        assertEquals(getTempOrders(), buyer.getOrders());
    }

    static List<Order> getTempOrders() {
        int size = 5;
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Order order = new Order(i, i * 2, new ArrayList<>());
            orders.add(order);
        }
        return orders;
    }
}