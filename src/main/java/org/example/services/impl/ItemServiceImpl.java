package org.example.services.impl;

import org.example.models.Item;
import org.example.repository.ItemRepository;
import org.example.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private ItemRepository itemRepository;
    public static final String INVALID_ID = "Invalid id";
    public static final String INVALID_ORDER_ID = "Invalid order id";
    public static final String SQL_QUERY_FAILED = "Sql query failed...";
    public static final String INVALID_ITEM_ID = "Invalid item id";

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item get(int id) {
        return itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER_ID));
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void update(Item item) {
        Item updateItem = get(item.getId());
        updateItem.setName(item.getName());
        updateItem.setPrice(item.getPrice());
        updateItem.setOrders(item.getOrders());
        itemRepository.save(updateItem);
    }

    @Override
    public void delete(int itemId) {
        itemRepository.deleteById(itemId);
    }
}