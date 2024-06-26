package org.example.controlers.mapper;

import org.example.controlers.dto.OrderDTO;
import org.example.models.Item;
import org.example.models.Order;
import org.example.repository.impl.ItemRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class OrderDtoMapperImpl implements OrderDtoMapper {
    ItemRepositoryImpl itemRepository;


    @Override
    public Order orderDTOToOrder(OrderDTO orderDTO) {
        List<Item> items = new ArrayList<>();
        for (int item : orderDTO.getItems()) {
            items.add(itemRepository.get(item));
        }
        return new Order(orderDTO.getId(), orderDTO.getNumber(), items);
    }

    @Override
    public OrderDTO orderToOrderDTO(Order order) {
        List<Integer> items = new ArrayList<>();
        for (Item item : order.getItems()) {
            items.add(item.getId());
        }
        return new OrderDTO(order.getId(), order.getNumber(), items);
    }
}