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
import static org.example.controlers.ItemControllerTest.getTemplateItem;
import static org.example.controlers.OrderControllerTest.getTemplateOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
class OrderRepositoryImplTest extends BaseTest {
    private final ItemRepositoryImpl itemRepository;
    private final OrderRepositoryImpl orderRepository;
    private final BuyerRepositoryImpl buyerRepository;

    @Autowired
    public OrderRepositoryImplTest(ItemRepositoryImpl itemRepository, OrderRepositoryImpl orderRepository,
                                   BuyerRepositoryImpl buyerRepository, JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.buyerRepository = buyerRepository;
    }

    @Test
    void get() {
        Order saveOrder = saveOrder(getTemplateOrder(1));
        Order getOrder = orderRepository.get(saveOrder.getId());
        assertEquals(getOrder, getTemplateOrder(1));
    }

    @Test
    void getAll() {
        int size = 5;
        List<Order> orders = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            Order order = saveOrder(getTemplateOrder(i));
            orders.add(order);
        }
        List<Order> getAllOrders = orderRepository.getAll();
        assertEquals(orders, getAllOrders);
    }

    @Test
    void save() {
        Order saveOrder = saveOrder(getTemplateOrder(1));
        Order getOrder = orderRepository.get(saveOrder.getId());
        assertEquals(getOrder, getTemplateOrder(1));
    }

    @Test
    void update() {
        Order saveOrder = saveOrder(getTemplateOrder(1));
        saveOrder.setNumber(33333);
        orderRepository.update(saveOrder);
        Order getUpdateItem = orderRepository.get(saveOrder.getId());
        assertEquals(getUpdateItem, saveOrder);
    }

    @Test
    void delete() {
        Order saveOrder = saveOrder(getTemplateOrder(1));
        assertEquals(orderRepository.get(saveOrder.getId()), saveOrder);
        orderRepository.delete(saveOrder.getId());
        assertNull(orderRepository.get(saveOrder.getId()));
    }

    @Test
    void getListOfBuyerOrdersById() {
        Buyer buyer = getTemplateBuyer(1);
        List<Order> orders = buyer.getOrders();
        List<Order> saveOrders = new ArrayList<>();
        for (Order order : orders) {
            Order saveOrder = orderRepository.save(order);
            saveOrders.add(saveOrder);
        }
        buyer.setOrders(saveOrders);
        Buyer saveBuyer = buyerRepository.save(buyer);
        List<Order> orderListActual = getTemplateBuyer(1).getOrders();
        List<Order> orderListExpected = orderRepository.getListOfBuyerOrdersById(saveBuyer.getId());
        assertEquals(orderListExpected, orderListActual);
    }

    @Test
    void getListOrderByIdItem() {
        int size = 5;
        Item item = getTemplateItem(1);
        List<Order> orderList = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            Order saveOrder = orderRepository.save(new Order(i, i, new ArrayList<>()));
            orderList.add(saveOrder);
        }
        item.setOrders(orderList);
        Item saveItem = itemRepository.save(item);
        List<Order> orderListActual = item.getOrders();
        List<Order> orderListExpected = orderRepository.getListOrderByIdItem(saveItem.getId());
        assertEquals(orderListExpected, orderListActual);
    }

    private Order saveOrder(Order order) {
        List<Item> items = order.getItems();
        List<Item> saveItems = new ArrayList<>();
        for (Item item : items) {
            Item saveItem = itemRepository.save(item);
            saveItems.add(saveItem);
        }
        order.setItems(saveItems);
        return orderRepository.save(order);
    }
}