package org.example.models;

import java.util.List;
import java.util.Objects;

public class Buyer {
    private int id;
    private String name;
    private List<Order> orders;

    public Buyer() {
    }

    public Buyer(int id, String name, List<Order> orders) {
        this.id = id;
        this.orders = orders;
        this.name = name;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", orders=" + orders +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buyer buyer = (Buyer) o;
        return Objects.equals(name, buyer.name) && Objects.equals(orders, buyer.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, orders);
    }
}