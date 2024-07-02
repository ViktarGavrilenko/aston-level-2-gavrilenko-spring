package org.example.controlers.mapper;

import org.example.controlers.dto.BuyerDTO;
import org.example.controlers.mapper.util.MappingUtil;
import org.example.models.Buyer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        MappingUtil.class
})
public interface BuyerDtoMapper {
    @Mapping(target = "orders", qualifiedByName = {"MappingUtil","getOrdersById"}, source = "orders")
    Buyer buyerDTOToBuyer(BuyerDTO buyerDTO);

    @Mapping(target = "orders", qualifiedByName = {"MappingUtil", "getIdOrders"}, source = "orders")
    BuyerDTO buyerToBuyerDTO(Buyer buyer);
}