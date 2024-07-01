package org.example.controlers;

import org.example.controlers.dto.BuyerDTO;
import org.example.controlers.mapper.BuyerDtoMapperImpl;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuyerControllerTest {
    public static Buyer getTemplateBuyer(int id) {
        List<Order> orders = new ArrayList<>();
        Order order = new Order(id, id, new ArrayList<>());
        orders.add(order);
        return new Buyer(id, "Name" + id, orders);
    }

    public static List<Buyer> buyerList(int size) {
        List<Buyer> buyerList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            buyerList.add(getTemplateBuyer(i));
        }
        return buyerList;
    }


    @InjectMocks
    private BuyerController buyerController;

    @Mock
    private BuyerServiceImpl buyerService;

    @Mock
    private BuyerDtoMapperImpl dtoMapper;

    /*    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<BuyerDTO> get(@PathVariable("id") int id) {
        Buyer buyer = buyerService.get(id);
        BuyerDTO buyerDTO = null;
        if (buyer != null) {
            buyerDTO = dtoMapper.buyerToBuyerDTO(buyer);
        }
        return new ResponseEntity<>(buyerDTO, HttpStatus.OK);
    }*/

    @Test
    void get() {
        int id = 1;
        BuyerDTO buyerDTO = new BuyerDTO(id, getTemplateBuyer(id).getName(), new ArrayList<>());
        when(buyerService.get(Mockito.anyInt())).thenReturn(getTemplateBuyer(id));
        ResponseEntity<BuyerDTO> response = buyerController.get(id);
        when(dtoMapper.buyerToBuyerDTO(any(Buyer.class))).thenReturn(buyerDTO);
        Mockito.verify(dtoMapper, times(1)).buyerToBuyerDTO(any(Buyer.class));
        assertEquals(buyerDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAll() {
    }

    @Test
    void save() {
    }

    @Test
    void doPut() {
    }

    @Test
    void doDelete() {
    }
}