package org.example.controlers.mapper;

import org.example.controlers.dto.ItemDTO;
import org.example.controlers.mapper.util.MappingUtil;
import org.example.models.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        MappingUtil.class
})
public interface ItemDtoMapper {
    @Mapping(target = "orders", qualifiedByName = {"MappingUtil", "getOrdersById"}, source = "orders")
    Item itemDTOToItem(ItemDTO itemDTO);

    @Mapping(target = "orders", qualifiedByName = {"MappingUtil", "getIdOrders"}, source = "orders")
    ItemDTO itemToItemDTO(Item item);
}