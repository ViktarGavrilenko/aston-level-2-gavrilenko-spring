package org.example.services.impl;

import org.example.models.Buyer;
import org.example.repository.BuyerRepository;
import org.example.services.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.services.impl.ItemServiceImpl.INVALID_ORDER_ID;

@Service
public class BuyerServiceImpl implements BuyerService {
    private BuyerRepository buyerRepository;

    @Autowired
    public BuyerServiceImpl(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    @Override
    public List<Buyer> getAll() {
        return buyerRepository.findAll();
    }

    @Override
    public Buyer get(int id) {
        return buyerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER_ID));
    }

    @Override
    public Buyer save(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    @Override
    public void update(Buyer buyer) {
        Buyer updateBuyer = get(buyer.getId());
        updateBuyer.setName(buyer.getName());
        updateBuyer.setOrders(buyer.getOrders());
        buyerRepository.save(updateBuyer);
    }

    @Override
    public void delete(int buyerId) {
        buyerRepository.deleteById(buyerId);
    }
}