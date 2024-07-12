package org.example.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "number")
    private int number;
    @ManyToMany()
    @JoinTable(
            name = "order_items",
            joinColumns = {@JoinColumn(name = "id_item")},
            inverseJoinColumns = {@JoinColumn(name = "id_order")})
    private List<Item> items;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_buyer")
    private Buyer buyer;

    public Order() {
    }

    public Order(int id, int number, List<Item> items) {
        this.id = id;
        this.number = number;
        this.items = items;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyers(Buyer buyer) {
        this.buyer = buyer;
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