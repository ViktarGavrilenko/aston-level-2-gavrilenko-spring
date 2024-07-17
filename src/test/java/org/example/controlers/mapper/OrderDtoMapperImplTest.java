package org.example.controlers.mapper;

import org.example.controlers.dto.OrderDTO;
import org.example.controlers.mapper.util.MappingUtil;
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
class OrderDtoMapperImplTest {

    @Mock
    private MappingUtil mappingUtil;
    @InjectMocks
    private OrderDtoMapperImpl dtoMapper;

/*    private OrderDTO getOrderDTO() {
        return new OrderDTO(1, 1, List.of(1, 2, 3));
    }*/

/*    private Order getOrder() {
        List<Item> items = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            List<Order> orders = new ArrayList<>();
            items.add(new Item(i, "name" + i, i * 2, orders));
        }
        return new Order(1, 1, items);
    }*/

/*    @Test
    void orderDTOToOrderTest() {
        when(mappingUtil.getItemsById(anyList())).thenReturn(getOrder().getItems());
        assertEquals(dtoMapper.orderDTOToOrder(getOrderDTO()), getOrder());
    }

    @Test
    void orderToOrderDTOTest() {
        when(mappingUtil.getIdItems(anyList())).thenReturn(getOrderDTO().getItems());
        assertEquals(dtoMapper.orderToOrderDTO(getOrder()), getOrderDTO());
    }*/
}