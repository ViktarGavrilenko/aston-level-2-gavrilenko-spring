package org.example.services.impl;

import org.example.models.Item;
import org.example.repository.ItemRepository;
import org.example.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.repository.impl.BuyerRepositoryImpl.INVALID_ORDER_ID;

@Service
public class ItemServiceImpl implements ItemService {
    private ItemRepository itemRepository;

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
        itemRepository.save(item);
    }

    @Override
    public void delete(int itemId) {
        itemRepository.deleteById(itemId);
    }
}