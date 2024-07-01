package org.example.controlers.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyerDTOTest {
    private final int id = 1;
    private final String name = "someName";

    @Test
    void testGetId() {
        BuyerDTO buyer = new BuyerDTO(id, name, new ArrayList<>());
        assertEquals(id, buyer.getId());
    }

    @Test
    void testSetId() {
        BuyerDTO buyer = new BuyerDTO();
        buyer.setId(id);
        assertEquals(id, buyer.getId());
    }

    @Test
    void testTestGetName() {
        BuyerDTO buyer = new BuyerDTO(id, name, new ArrayList<>());
        assertEquals(name, buyer.getName());
    }

    @Test
    void testTestSetName() {
        BuyerDTO buyer = new BuyerDTO();
        buyer.setName(name);
        assertEquals(name, buyer.getName());
    }

    @Test
    void testGetOrders() {
        BuyerDTO buyer = new BuyerDTO(id, name, getTempListInteger());
        assertEquals(getTempListInteger(), buyer.getOrders());
    }

    @Test
    void testSetOrders() {
        BuyerDTO buyer = new BuyerDTO();
        buyer.setOrders(getTempListInteger());
        assertEquals(getTempListInteger(), buyer.getOrders());
    }

    static List<Integer> getTempListInteger() {
        int size = 5;
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            integers.add(i);
        }
        return integers;
    }
}