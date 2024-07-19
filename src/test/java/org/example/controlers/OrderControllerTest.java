package org.example.controlers;

import org.example.controlers.dto.OrderDTO;
import org.example.controlers.mapper.OrderDtoMapper;
import org.example.models.Buyer;
import org.example.models.Item;
import org.example.models.Order;
import org.example.services.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    @InjectMocks
    private OrderController orderController;
    @Mock
    private OrderServiceImpl orderService;
    @Mock
    private OrderDtoMapper dtoMapper;

    public static Order getTemplateOrder(int id) {
        List<Item> items = new ArrayList<>();
        Item item = new Item(id, "Name" + id, id, new ArrayList<>());
        items.add(item);
        return new Order(id, id, items, new Buyer());
    }

    public static List<Order> getOrderList(int size) {
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            orderList.add(getTemplateOrder(i));
        }
        return orderList;
    }

    public static List<OrderDTO> orderDTOList(int size) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            orderDTOS.add(new OrderDTO(i, i, List.of(i), i));
        }
        return orderDTOS;
    }

    @Test
    void get() {
        int id = 1;
        OrderDTO orderDTO = new OrderDTO(id, id, new ArrayList<>(), id);
        when(orderService.get(Mockito.anyInt())).thenReturn(getTemplateOrder(id));
        when(dtoMapper.orderToOrderDTO(any(Order.class))).thenReturn(orderDTO);
        ResponseEntity<OrderDTO> response = orderController.get(id);
        Mockito.verify(dtoMapper, times(1)).orderToOrderDTO(any(Order.class));
        assertEquals(orderDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAll() {
        int size = 3;
        List<OrderDTO> itemDTOS = orderDTOList(size);
        when(orderService.getAll()).thenReturn(getOrderList(size));

        doAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            return new OrderDTO(order.getId(), order.getNumber(), order.getItems().stream()
                    .map(Item::getId)
                    .toList(), order.getBuyer().getId());
        }).when(dtoMapper).orderToOrderDTO(any(Order.class));

        ResponseEntity<List<OrderDTO>> response = orderController.getAll();
        Mockito.verify(dtoMapper, times(size)).orderToOrderDTO(any(Order.class));
        assertEquals(itemDTOS, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void save() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/orderDTO.json")));
        doAnswer(invocation -> {
            OrderDTO orderDTO = invocation.getArgument(0);
            return new Order(orderDTO.getId(), orderDTO.getNumber(), orderDTO.getItems().stream()
                    .map(i -> new Item(i, "Name" + i, i, new ArrayList<>()))
                    .toList(), new Buyer(orderDTO.getBuyerId(), "SomeName", new ArrayList<>()));
        }).when(dtoMapper).orderDTOToOrder(any(OrderDTO.class));
        ResponseEntity<?> response = orderController.save(json);
        Mockito.verify(dtoMapper, times(1)).orderDTOToOrder(any(OrderDTO.class));
        Mockito.verify(orderService, times(1)).save(any(Order.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void doPut() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/orderDTO.json")));
        doAnswer(invocation -> {
            OrderDTO orderDTO = invocation.getArgument(0);
            return new Order(orderDTO.getId(), orderDTO.getNumber(), orderDTO.getItems().stream()
                    .map(i -> new Item(i, "Name" + i, i, new ArrayList<>()))
                    .toList(), new Buyer(orderDTO.getBuyerId(), "SomeName", new ArrayList<>()));
        }).when(dtoMapper).orderDTOToOrder(any(OrderDTO.class));
        ResponseEntity<?> response = orderController.doPut(json);
        Mockito.verify(dtoMapper, times(1)).orderDTOToOrder(any(OrderDTO.class));
        Mockito.verify(orderService, times(1)).update(any(Order.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void doDelete() {
        int id = 1;
        ResponseEntity<?> response = orderController.doDelete(id);
        Mockito.verify(orderService, times(1)).delete(anyInt());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}