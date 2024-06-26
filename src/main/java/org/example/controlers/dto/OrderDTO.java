package org.example.controlers.dto;

import java.util.List;
import java.util.Objects;

public class OrderDTO {
    private int id;
    private int number;
    private List<Integer> items;

    public OrderDTO() {
    }

    public OrderDTO(int id, int number, List<Integer> items) {
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

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return number == orderDTO.number && Objects.equals(items, orderDTO.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, items);
    }
}