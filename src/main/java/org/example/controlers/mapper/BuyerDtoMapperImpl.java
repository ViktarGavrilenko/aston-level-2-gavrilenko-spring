package org.example.controlers.mapper;

import org.example.controlers.dto.BuyerDTO;
import org.example.models.Buyer;
import org.example.models.Order;
import org.example.repository.impl.OrderRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class BuyerDtoMapperImpl implements BuyerDtoMapper {
    OrderRepositoryImpl orderRepository;

    @Autowired
    public BuyerDtoMapperImpl(OrderRepositoryImpl orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Buyer buyerDTOToBuyer(BuyerDTO buyerDTO) {
        List<Order> orders = new ArrayList<>();
        for (int idOrder : buyerDTO.getOrders()) {
            orders.add(orderRepository.get(idOrder));
        }
        return new Buyer(buyerDTO.getId(), buyerDTO.getName(), orders);
    }

    @Override
    public BuyerDTO buyerToBuyerDTO(Buyer buyer) {
        List<Integer> orders = new ArrayList<>();
        for (Order order : buyer.getOrders()) {
            orders.add(order.getId());
        }
        return new BuyerDTO(buyer.getId(), buyer.getName(), orders);
    }
}