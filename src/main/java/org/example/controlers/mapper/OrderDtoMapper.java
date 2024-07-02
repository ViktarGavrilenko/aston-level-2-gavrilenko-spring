package org.example.controlers.mapper;

import org.example.controlers.dto.OrderDTO;
import org.example.controlers.mapper.util.MappingUtil;
import org.example.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        MappingUtil.class
})
public interface OrderDtoMapper {
    @Mapping(target = "items", qualifiedByName = {"MappingUtil", "getItemsById"}, source = "items")
    Order orderDTOToOrder(OrderDTO orderDTO);

    @Mapping(target = "items", qualifiedByName = {"MappingUtil", "getIdItems"}, source = "items")
    OrderDTO orderToOrderDTO(Order order);
}
