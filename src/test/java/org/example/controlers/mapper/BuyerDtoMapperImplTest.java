package org.example.controlers.mapper;

import org.example.controlers.dto.BuyerDTO;
import org.example.models.Buyer;
import org.example.models.Item;
import org.example.models.Order;
import org.example.repository.impl.OrderRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class BuyerDtoMapperImplTest {
    @Mock
    OrderRepositoryImpl orderRepository;

    @InjectMocks
    BuyerDtoMapper dtoMapper;

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
        Mockito.doAnswer(i -> {
            int number = i.getArgument(0);
            List<Item> items = new ArrayList<>();
            return new Order(number, number, items);
        }).when(orderRepository).get(anyInt());
        assertEquals(dtoMapper.buyerDTOToBuyer(getBuyerDTO()), getBuyer());
    }

    @Test
    void buyerToBuyerDTO() {
        assertEquals(dtoMapper.buyerToBuyerDTO(getBuyer()), getBuyerDTO());
    }
}