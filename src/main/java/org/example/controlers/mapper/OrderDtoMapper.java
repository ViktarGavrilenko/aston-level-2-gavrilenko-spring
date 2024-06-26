package org.example.controlers.mapper;

import org.example.controlers.dto.OrderDTO;
import org.example.models.Order;

import org.mapstruct.Mapper;

@Mapper
public interface OrderDtoMapper {
    Order orderDTOToOrder(OrderDTO orderDTO);

    OrderDTO orderToOrderDTO(Order order);
}
