package org.example.controlers;

import org.example.controlers.dto.BuyerDTO;
import org.example.controlers.mapper.BuyerDtoMapper;
import org.example.models.Buyer;
import org.example.models.Order;
import org.example.services.impl.BuyerServiceImpl;
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
public class BuyerControllerTest {
    @InjectMocks
    private BuyerController buyerController;
    @Mock
    private BuyerServiceImpl buyerService;
    @Mock
    private BuyerDtoMapper dtoMapper;

    public static Buyer getTemplateBuyer(int id) {
        List<Order> orders = new ArrayList<>();
        Order order = new Order(id, id, new ArrayList<>());
        orders.add(order);
        return new Buyer(id, "Name" + id, orders);
    }

    public static List<Buyer> buyerList(int size) {
        List<Buyer> buyerList = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            buyerList.add(getTemplateBuyer(i));
        }
        return buyerList;
    }

    public static List<BuyerDTO> buyerDTOList(int size) {
        List<BuyerDTO> buyerList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            buyerList.add(new BuyerDTO(i, "Name" + i, List.of(i)));
        }
        return buyerList;
    }

    @Test
    void get() {
        int id = 1;
        BuyerDTO buyerDTO = new BuyerDTO(id, getTemplateBuyer(id).getName(), new ArrayList<>());
        when(buyerService.get(Mockito.anyInt())).thenReturn(getTemplateBuyer(id));
        when(dtoMapper.buyerToBuyerDTO(any(Buyer.class))).thenReturn(buyerDTO);
        ResponseEntity<BuyerDTO> response = buyerController.get(id);
        Mockito.verify(dtoMapper, times(1)).buyerToBuyerDTO(any(Buyer.class));
        assertEquals(buyerDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAll() {
        int size = 3;
        List<BuyerDTO> buyerDTOS = buyerDTOList(size);
        when(buyerService.getAll()).thenReturn(buyerList(size));

        doAnswer(invocation -> {
            Buyer buyer = invocation.getArgument(0);
            return new BuyerDTO(buyer.getId(), buyer.getName(), buyer.getOrders().stream()
                    .map(Order::getId)
                    .toList());
        }).when(dtoMapper).buyerToBuyerDTO(any(Buyer.class));

        ResponseEntity<List<BuyerDTO>> response = buyerController.getAll();
        Mockito.verify(dtoMapper, times(size)).buyerToBuyerDTO(any(Buyer.class));
        assertEquals(buyerDTOS, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void save() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/buyerDTO.json")));
        doAnswer(invocation -> {
            BuyerDTO buyerDTO = invocation.getArgument(0);
            return new Buyer(buyerDTO.getId(), buyerDTO.getName(), buyerDTO.getOrders().stream()
                    .map(i -> new Order(i, i, new ArrayList<>()))
                    .toList());
        }).when(dtoMapper).buyerDTOToBuyer(any(BuyerDTO.class));
        ResponseEntity<?> response = buyerController.save(json);
        Mockito.verify(dtoMapper, times(1)).buyerDTOToBuyer(any(BuyerDTO.class));
        Mockito.verify(buyerService, times(1)).save(any(Buyer.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void doPut() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/buyerDTO.json")));
        doAnswer(invocation -> {
            BuyerDTO buyerDTO = invocation.getArgument(0);
            return new Buyer(buyerDTO.getId(), buyerDTO.getName(), buyerDTO.getOrders().stream()
                    .map(i -> new Order(i, i, new ArrayList<>()))
                    .toList());
        }).when(dtoMapper).buyerDTOToBuyer(any(BuyerDTO.class));
        ResponseEntity<?> response = buyerController.doPut(json);
        Mockito.verify(dtoMapper, times(1)).buyerDTOToBuyer(any(BuyerDTO.class));
        Mockito.verify(buyerService, times(1)).update(any(Buyer.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void doDelete() {
        int id = 1;
        ResponseEntity<?> response = buyerController.doDelete(id);
        Mockito.verify(buyerService, times(1)).delete(anyInt());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}