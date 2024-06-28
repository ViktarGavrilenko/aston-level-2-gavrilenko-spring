package org.example.controlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controlers.dto.OrderDTO;
import org.example.controlers.mapper.OrderDtoMapperImpl;
import org.example.models.Order;
import org.example.services.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderServiceImpl orderService;
    private final OrderDtoMapperImpl dtoMapper;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public OrderController(OrderServiceImpl orderService, OrderDtoMapperImpl dtoMapper) {
        this.orderService = orderService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<OrderDTO> get(@PathVariable("id") int id) {
        Order order = orderService.get(id);
        OrderDTO orderDTO = null;
        if (order != null) {
            orderDTO = dtoMapper.orderToOrderDTO(order);
        }
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<OrderDTO>> getAll() {
        List<Order> orders = orderService.getAll();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            orderDTOS.add(dtoMapper.orderToOrderDTO(order));
        }
        return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> save(@RequestBody String json) throws JsonProcessingException {
        OrderDTO orderDTO = mapper.readValue(json, OrderDTO.class);
        Order order = dtoMapper.orderDTOToOrder(orderDTO);
        orderService.save(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    protected ResponseEntity<?> doPut(@RequestBody String json) throws JsonProcessingException {
        OrderDTO orderDTO = mapper.readValue(json, OrderDTO.class);
        Order order = dtoMapper.orderDTOToOrder(orderDTO);
        orderService.update(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    protected ResponseEntity<?> doDelete(@RequestParam("id") int id) {
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}