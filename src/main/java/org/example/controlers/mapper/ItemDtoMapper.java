package org.example.controlers.mapper;

import org.example.controlers.dto.ItemDTO;
import org.example.models.Item;

import org.mapstruct.Mapper;

@Mapper
public interface ItemDtoMapper {
    Item itemDTOToItem(ItemDTO itemDTO);

    ItemDTO itemToItemDTO(Item item);
}
