package org.example.controlers.mapper;

import org.example.controlers.dto.OrderDTO;
import org.example.models.Item;
import org.example.models.Order;
import org.example.repository.impl.ItemRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDtoMapperImpl implements OrderDtoMapper {
    ItemRepositoryImpl itemRepository;

    @Autowired
    public OrderDtoMapperImpl(ItemRepositoryImpl itemRepository) {
        this.itemRepository = itemRepository;
    }

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