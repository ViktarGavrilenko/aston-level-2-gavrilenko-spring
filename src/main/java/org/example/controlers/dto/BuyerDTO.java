package org.example.controlers.dto;

import java.util.List;
import java.util.Objects;

public class BuyerDTO {
    private int id;
    private String name;
    private List<Integer> orders;

    public BuyerDTO() {
    }

    public BuyerDTO(int id, String name, List<Integer> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getOrders() {
        return orders;
    }

    public void setOrders(List<Integer> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyerDTO buyerDTO = (BuyerDTO) o;
        return Objects.equals(name, buyerDTO.name) && Objects.equals(orders, buyerDTO.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, orders);
    }
}