package org.example.services;

import org.example.models.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAll();

    Item get(int id);

    Item save(Item item);

    void update(Item item);

    void delete(int itemId);
}
