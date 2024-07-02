package org.example.controlers.mapper.util;

import org.example.models.Item;
import org.example.models.Order;
import org.example.repository.impl.ItemRepositoryImpl;
import org.example.repository.impl.OrderRepositoryImpl;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Named("MappingUtil")
@Component
public class MappingUtil {
    private final OrderRepositoryImpl orderRepository;
    private final ItemRepositoryImpl itemRepository;

    @Autowired
    public MappingUtil(OrderRepositoryImpl orderRepository, ItemRepositoryImpl itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    @Named("getIdOrders")
    public List<Integer> getIdOrders(List<Order> orders) {
        List<Integer> idOrders = new ArrayList<>();
        if (orders != null) {
            for (Order order : orders) {
                idOrders.add(order.getId());
            }
        }
        return idOrders;
    }

    @Named("getOrdersById")
    public List<Order> getOrdersById(List<Integer> idOrders) {
        List<Order> orders = new ArrayList<>();
        if (idOrders != null) {
            for (int id : idOrders) {
                orders.add(orderRepository.get(id));
            }
        }
        return orders;
    }

    @Named("getItemsById")
    public List<Item> getItemsById(List<Integer> itemDTO) {
        List<Item> items = new ArrayList<>();
        for (int item : itemDTO) {
            items.add(itemRepository.get(item));
        }
        return items;
    }

    @Named("getIdItems")
    public List<Integer> getIdItems(List<Item> items) {
        List<Integer> idItems = new ArrayList<>();
        if (items != null) {
            for (Item item : items) {
                idItems.add(item.getId());
            }
        }
        return idItems;
    }
}