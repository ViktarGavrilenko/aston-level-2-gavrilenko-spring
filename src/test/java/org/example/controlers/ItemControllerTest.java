package org.example.controlers;

import org.example.controlers.dto.ItemDTO;
import org.example.controlers.mapper.ItemDtoMapper;
import org.example.models.Item;
import org.example.models.Order;
import org.example.services.impl.ItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {
    @InjectMocks
    private ItemController itemController;
    @Mock
    private ItemServiceImpl itemService;
    @Mock
    private ItemDtoMapper dtoMapper;

    public static Item getTemplateItem(int id) {
        List<Order> orders = new ArrayList<>();
        return new Item(id, "Name" + id, id, orders);
    }

    public static List<Item> getItemList(int size) {
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            itemList.add(getTemplateItem(i));
        }
        return itemList;
    }

    public static List<ItemDTO> itemDTOList(int size) {
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            itemDTOS.add(new ItemDTO(i, "Name" + i, i, new ArrayList<>()));
        }
        return itemDTOS;
    }

    @Test
    void get() {
        int id = 1;
        ItemDTO itemDTO = new ItemDTO(id, getTemplateItem(id).getName(), id, new ArrayList<>());
        when(itemService.get(Mockito.anyInt())).thenReturn(getTemplateItem(id));
        when(dtoMapper.itemToItemDTO(any(Item.class))).thenReturn(itemDTO);
        ResponseEntity<ItemDTO> response = itemController.get(id);
        Mockito.verify(dtoMapper, times(1)).itemToItemDTO(any(Item.class));
        assertEquals(itemDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAll() {
        int size = 3;
        List<ItemDTO> itemDTOS = itemDTOList(size);
        when(itemService.getAll()).thenReturn(getItemList(size));

        doAnswer(invocation -> {
            Item item = invocation.getArgument(0);
            return new ItemDTO(item.getId(), item.getName(), item.getPrice(), item.getOrders().stream()
                    .map(Order::getId)
                    .toList());
        }).when(dtoMapper).itemToItemDTO(any(Item.class));

        ResponseEntity<List<ItemDTO>> response = itemController.getAll();
        Mockito.verify(dtoMapper, times(size)).itemToItemDTO(any(Item.class));
        assertEquals(itemDTOS, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

/*    @Test
    void save() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/itemDTO.json")));
        doAnswer(invocation -> {
            ItemDTO itemDTO = invocation.getArgument(0);
            return new Item(itemDTO.getId(), itemDTO.getName(), itemDTO.getPrice(), itemDTO.getOrders().stream()
                    .map(i -> new Order(i, i, new ArrayList<>()))
                    .toList());
        }).when(dtoMapper).itemDTOToItem(any(ItemDTO.class));
        ResponseEntity<?> response = itemController.save(json);
        Mockito.verify(dtoMapper, times(1)).itemDTOToItem(any(ItemDTO.class));
        Mockito.verify(itemService, times(1)).save(any(Item.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void doPut() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/itemDTO.json")));
        doAnswer(invocation -> {
            ItemDTO itemDTO = invocation.getArgument(0);
            return new Item(itemDTO.getId(), itemDTO.getName(), itemDTO.getPrice(), itemDTO.getOrders().stream()
                    .map(i -> new Order(i, i, new ArrayList<>()))
                    .toList());
        }).when(dtoMapper).itemDTOToItem(any(ItemDTO.class));
        ResponseEntity<?> response = itemController.doPut(json);
        Mockito.verify(dtoMapper, times(1)).itemDTOToItem(any(ItemDTO.class));
        Mockito.verify(itemService, times(1)).update(any(Item.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }*/

    @Test
    void doDelete() {
        int id = 1;
        ResponseEntity<?> response = itemController.doDelete(id);
        Mockito.verify(itemService, times(1)).delete(anyInt());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}