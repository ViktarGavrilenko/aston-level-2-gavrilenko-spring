package org.example.services.impl;

import org.example.models.Buyer;
import org.example.repository.impl.BuyerRepositoryImpl;
import org.example.services.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyerServiceImpl implements BuyerService {
    private BuyerRepositoryImpl buyerRepository;

    @Autowired
    public BuyerServiceImpl(BuyerRepositoryImpl buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    @Override
    public List<Buyer> getAll() {
        return buyerRepository.getAll();
    }

    @Override
    public Buyer get(int id) {
        return buyerRepository.get(id);
    }

    @Override
    public Buyer save(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    @Override
    public void update(Buyer buyer) {
        buyerRepository.update(buyer);
    }

    @Override
    public void delete(int buyerId) {
        buyerRepository.delete(buyerId);
    }
}