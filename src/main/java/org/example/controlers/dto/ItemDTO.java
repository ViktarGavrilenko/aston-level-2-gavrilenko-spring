package org.example.controlers.dto;

import java.util.List;
import java.util.Objects;

public class ItemDTO {
    private int id;
    private String name;
    private int price;
    private List<Integer> orders;

    public ItemDTO() {
    }

    public ItemDTO(int id, String name, int price, List<Integer> orders) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
        ItemDTO itemDTO = (ItemDTO) o;
        return price == itemDTO.price && Objects.equals(name, itemDTO.name) && Objects.equals(orders, itemDTO.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, orders);
    }
}