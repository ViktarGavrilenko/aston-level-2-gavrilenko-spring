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
    @Mapping(target = "buyer", qualifiedByName = {"MappingUtil", "getBuyerById"}, source = "buyerId")
    Order orderDTOToOrder(OrderDTO orderDTO);

    @Mapping(target = "items", qualifiedByName = {"MappingUtil", "getIdItems"}, source = "items")
    @Mapping(target = "buyerId", qualifiedByName = {"MappingUtil", "getIdBuyer"}, source = "buyer")
    OrderDTO orderToOrderDTO(Order order);
}
