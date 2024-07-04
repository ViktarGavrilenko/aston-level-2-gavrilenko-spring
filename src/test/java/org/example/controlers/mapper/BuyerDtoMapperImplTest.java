package org.example.controlers.mapper;

import org.example.controlers.dto.BuyerDTO;
import org.example.controlers.mapper.util.MappingUtil;
import org.example.models.Buyer;
import org.example.models.Item;
import org.example.models.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuyerDtoMapperImplTest {
    @Mock
    private MappingUtil mappingUtil;
    @InjectMocks
    private BuyerDtoMapperImpl dtoMapper;

    private BuyerDTO getBuyerDTO() {
        return new BuyerDTO(1, "name", List.of(1, 2, 3));
    }

    private Buyer getBuyer() {
        List<Order> orders = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            List<Item> items = new ArrayList<>();
            orders.add(new Order(i, i, items));
        }
        return new Buyer(1, "name", orders);
    }

    @Test
    void buyerDTOToBuyer() {
        when(mappingUtil.getOrdersById(anyList())).thenReturn(getBuyer().getOrders());
        assertEquals(dtoMapper.buyerDTOToBuyer(getBuyerDTO()), getBuyer());
    }

    @Test
    void buyerToBuyerDTO() {
        when(mappingUtil.getIdOrders(anyList())).thenReturn(getBuyerDTO().getOrders());
        assertEquals(dtoMapper.buyerToBuyerDTO(getBuyer()), getBuyerDTO());
    }
}