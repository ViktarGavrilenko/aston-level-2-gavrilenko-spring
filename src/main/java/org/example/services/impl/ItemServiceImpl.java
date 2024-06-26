package org.example.services.impl;

import org.example.models.Item;
import org.example.repository.impl.ItemRepositoryImpl;
import org.example.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private ItemRepositoryImpl itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepositoryImpl itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> getAll() {
        return itemRepository.getAll();
    }

    @Override
    public Item get(int id) {
        return itemRepository.get(id);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void update(Item item) {
        itemRepository.update(item);
    }

    @Override
    public void delete(int itemId) {
        itemRepository.delete(itemId);
    }
}