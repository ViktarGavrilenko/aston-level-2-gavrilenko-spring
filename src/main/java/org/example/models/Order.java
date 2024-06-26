package org.example.models;

import java.util.List;
import java.util.Objects;

public class Order {
    private int id;
    private int number;
    private List<Item> items;

    public Order() {
    }

    public Order(int id, int number, List<Item> items) {
        this.id = id;
        this.number = number;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", number=" + number +
                ", items=" + items +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return number == order.number && Objects.equals(items, order.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, items);
    }
}