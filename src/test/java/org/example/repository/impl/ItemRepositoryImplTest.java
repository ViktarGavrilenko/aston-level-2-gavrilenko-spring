/*
package org.example.repository.impl;

import org.example.models.Item;
import org.example.models.Order;
import org.example.repository.ItemRepository;
import org.example.repository.OrderRepository;
import org.example.repository.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.example.controlers.ItemControllerTest.getItemList;
import static org.example.controlers.ItemControllerTest.getTemplateItem;
import static org.example.controlers.OrderControllerTest.getTemplateOrder;
import static org.example.services.impl.ItemServiceImpl.INVALID_ORDER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
class ItemRepositoryImplTest extends BaseTest {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public ItemRepositoryImplTest(ItemRepository itemRepository, OrderRepository orderRepository, JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }

    @Test
    void get() {
        Item saveItem = itemRepository.save(getTemplateItem(1));
        Item getItem = itemRepository.findById(saveItem.getId()).orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER_ID));
        assertEquals(getItem, getTemplateItem(1));
    }

    @Test
    void getAll() {
        int size = 5;
        List<Item> items = getItemList(size);
        for (Item item : items) {
            itemRepository.save(item);
        }
        List<Item> getAllItems = itemRepository.findAll();
        assertEquals(getAllItems, items);
    }

    @Test
    void save() {
        Item saveItem = itemRepository.save(getTemplateItem(1));
        Item getItem = itemRepository.findById(saveItem.getId()).orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER_ID));
        assertEquals(getItem, getTemplateItem(1));
    }

    @Test
    void update() {
        Item saveItem = itemRepository.save(getTemplateItem(1));
        saveItem.setName("NewName");
        itemRepository.save(saveItem);
        Item getUpdateItem = itemRepository.findById(saveItem.getId()).orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER_ID));
        assertEquals(getUpdateItem, saveItem);
    }

    @Test
    void delete() {
        Item saveItem = itemRepository.save(getTemplateItem(1));
        assertEquals(itemRepository.findById(saveItem.getId()).orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER_ID)), saveItem);
        itemRepository.deleteById(saveItem.getId());
        assertNull(itemRepository.findById(saveItem.getId()).orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER_ID)));
    }

    @Test
    void getListItemsInOrderById() {
        Order order = getTemplateOrder(1);
        List<Item> items = new ArrayList<>();
        for (Item item : order.getItems()) {
            Item saveItem = itemRepository.save(item);
            items.add(saveItem);
        }
        order.setItems(items);
        Order saveOrder = orderRepository.save(order);
        List<Item> itemListActual = getTemplateOrder(1).getItems();
        List<Item> itemListExpected = itemRepository.findAllByOrder(saveOrder.getId());
        assertEquals(itemListExpected, itemListActual);
    }
}*/
