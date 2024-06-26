package org.example.services.impl;

import org.example.models.Order;
import org.example.repository.impl.OrderRepositoryImpl;
import org.example.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    OrderRepositoryImpl repository;

    @Autowired
    public OrderServiceImpl(OrderRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public List<Order> getAll() {
        return repository.getAll();
    }

    @Override
    public Order get(int id) {
        return repository.get(id);
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

    @Override
    public void update(Order order) {
        repository.update(order);
    }

    @Override
    public void delete(int orderId) {
        repository.delete(orderId);
    }
}