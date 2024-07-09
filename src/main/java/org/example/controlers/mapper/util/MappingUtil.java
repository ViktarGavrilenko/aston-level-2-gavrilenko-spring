package org.example.controlers.mapper.util;

import org.example.models.Item;
import org.example.models.Order;
import org.example.repository.ItemRepository;
import org.example.repository.impl.OrderRepositoryImpl;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.example.repository.impl.BuyerRepositoryImpl.INVALID_ORDER_ID;
import static org.example.repository.impl.OrderRepositoryImpl.INVALID_ITEM_ID;

@Named("MappingUtil")
@Component
public class MappingUtil {
    private final OrderRepositoryImpl orderRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public MappingUtil(OrderRepositoryImpl orderRepository, ItemRepository itemRepository) {
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
                Order order = orderRepository.get(id);
                if (order != null) {
                    orders.add(order);
                } else {
                    throw new IllegalArgumentException(INVALID_ORDER_ID);
                }
            }
        }
        return orders;
    }

    @Named("getItemsById")
    public List<Item> getItemsById(List<Integer> idItems) {
        List<Item> items = new ArrayList<>();
        if (idItems != null) {
            for (int id : idItems) {
                Item item = itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER_ID));
                if (item != null) {
                    items.add(item);
                } else {
                    throw new IllegalArgumentException(INVALID_ITEM_ID);
                }
            }
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