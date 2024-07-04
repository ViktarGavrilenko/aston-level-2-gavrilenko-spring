package org.example.services.impl;

import org.example.models.Item;
import org.example.repository.impl.ItemRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.example.controlers.ItemControllerTest.getTemplateItem;
import static org.example.controlers.ItemControllerTest.itemList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {
    @Mock
    private ItemRepositoryImpl itemRepository;
    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void getAll() {
        when(itemRepository.getAll()).thenReturn(itemList(5));
        List<Item> getAllItem = itemService.getAll();
        Assertions.assertEquals(getAllItem, itemList(5));
    }

    @Test
    void get() {
        when(itemRepository.get(Mockito.anyInt())).thenReturn(getTemplateItem(1));
        Item getItem = itemService.get(1);
        Assertions.assertEquals(getItem, getTemplateItem(1));
    }

    @Test
    void save() {
        when(itemRepository.save(Mockito.any(Item.class))).thenReturn(getTemplateItem(1));
        Item saveItem = itemService.save(getTemplateItem(1));
        Assertions.assertEquals(saveItem, getTemplateItem(1));
    }

    @Test
    void update() {
        itemService.update(getTemplateItem(1));
        Mockito.verify(itemRepository).update(getTemplateItem(1));
    }

    @Test
    void delete() {
        itemService.delete(1);
        Mockito.verify(itemRepository).delete(1);
    }
}