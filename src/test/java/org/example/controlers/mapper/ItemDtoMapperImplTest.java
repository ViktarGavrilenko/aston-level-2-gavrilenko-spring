package org.example.controlers.mapper;

import org.example.controlers.dto.ItemDTO;
import org.example.controlers.mapper.util.MappingUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ItemDtoMapperImplTest {
    @Mock
    private MappingUtil mappingUtil;
    @InjectMocks
    ItemDtoMapperImpl dtoMapper;

    private ItemDTO getItemDTO() {
        return new ItemDTO(1, "name", 1, List.of(1, 2, 3));
    }

/*    private Item getItem() {
        List<Order> orders = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            List<Item> items = new ArrayList<>();
            orders.add(new Order(i, i, items));
        }
        return new Item(1, "name", 1, orders);
    }*/
/*
    @Test
    void itemDTOToItemTest() {
        when(mappingUtil.getOrdersById(anyList())).thenReturn(getItem().getOrders());
        assertEquals(dtoMapper.itemDTOToItem(getItemDTO()), getItem());
    }

    @Test
    void itemToItemDTOTest() {
        when(mappingUtil.getIdOrders(anyList())).thenReturn(getItemDTO().getOrders());
        assertEquals(dtoMapper.itemToItemDTO(getItem()), getItemDTO());
    }*/
}