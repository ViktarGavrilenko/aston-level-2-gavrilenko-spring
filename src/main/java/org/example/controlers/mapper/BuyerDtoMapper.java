package org.example.controlers.mapper;

import org.example.controlers.dto.BuyerDTO;
import org.example.models.Buyer;

import org.mapstruct.Mapper;

@Mapper
public interface BuyerDtoMapper {
    Buyer buyerDTOToBuyer(BuyerDTO buyerDTO);

    BuyerDTO buyerToBuyerDTO(Buyer buyer);
}