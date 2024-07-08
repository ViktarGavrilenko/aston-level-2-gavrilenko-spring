package org.example.repository.impl;

import org.example.models.Buyer;
import org.example.models.Item;
import org.example.models.Order;
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

import static org.example.controlers.BuyerControllerTest.getTemplateBuyer;
import static org.example.controlers.ItemControllerTest.getItemList;
import static org.example.controlers.ItemControllerTest.getTemplateItem;
import static org.example.controlers.OrderControllerTest.getTemplateOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
class ItemRepositoryImplTest extends BaseTest {
    private final ItemRepositoryImpl itemRepository;
    private final OrderRepositoryImpl orderRepository;

    @Autowired
    public ItemRepositoryImplTest(ItemRepositoryImpl itemRepository, OrderRepositoryImpl orderRepository, JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }

    @Test
    void get() {
        Item saveItem = itemRepository.save(getTemplateItem(1));
        Item getItem = itemRepository.get(saveItem.getId());
        assertEquals(getItem, getTemplateItem(1));
    }

    @Test
    void getAll() {
        int size = 5;
        List<Item> items = getItemList(size);
        for (Item item : items) {
            itemRepository.save(item);
        }
        List<Item> getAllItems = itemRepository.getAll();
        assertEquals(getAllItems, items);
    }

    @Test
    void save() {
        Item saveItem = itemRepository.save(getTemplateItem(1));
        Item getItem = itemRepository.get(saveItem.getId());
        assertEquals(getItem, getTemplateItem(1));
    }

    @Test
    void update() {
        Item saveItem = itemRepository.save(getTemplateItem(1));
        saveItem.setName("NewName");
        itemRepository.update(saveItem);
        Item getUpdateItem = itemRepository.get(saveItem.getId());
        assertEquals(getUpdateItem, saveItem);
    }

    @Test
    void delete() {
        Item saveItem = itemRepository.save(getTemplateItem(1));
        assertEquals(itemRepository.get(saveItem.getId()), saveItem);
        itemRepository.delete(saveItem.getId());
        assertNull(itemRepository.get(saveItem.getId()));
    }

    @Test
    void getListItemsInOrderById() {
        Order order = getTemplateOrder(1);
        List<Item> items = new ArrayList<>();
        for (Item item: order.getItems()){
            Item saveItem = itemRepository.save(item);
            items.add(saveItem);
        }
        order.setItems(items);
        Order saveOrder = orderRepository.save(order);
        List<Item> itemListActual = getTemplateOrder(1).getItems();
        List<Item> itemListExpected = itemRepository.getListItemsInOrderById(saveOrder.getId());
        assertEquals(itemListExpected, itemListActual);
     }
}