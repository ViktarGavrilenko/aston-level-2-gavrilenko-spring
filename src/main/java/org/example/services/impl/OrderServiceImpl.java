package org.example.services.impl;

import org.example.models.Order;
import org.example.repository.OrderRepository;
import org.example.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.services.impl.ItemServiceImpl.INVALID_ORDER_ID;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order get(int id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER_ID));
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void update(Order order) {
        Order updateOrder = get(order.getId());
        updateOrder.setNumber(order.getNumber());
        updateOrder.setItems(order.getItems());
        orderRepository.save(updateOrder);
    }

    @Override
    public void delete(int orderId) {
        orderRepository.deleteById(orderId);
    }
}