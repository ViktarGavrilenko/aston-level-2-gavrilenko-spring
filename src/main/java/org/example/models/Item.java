package org.example.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_id_seq")
    @SequenceGenerator(name = "item_id_seq", allocationSize = 1)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<Order> orders;

    public Item() {
    }

    public Item(int id, String name, int price, List<Order> orders) {
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return price == item.price && Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}