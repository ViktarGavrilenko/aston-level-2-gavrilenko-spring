package org.example.services;

import org.example.models.Buyer;

import java.util.List;

public interface BuyerService {
    List<Buyer> getAll();

    Buyer get(int id);

    Buyer save(Buyer buyer);

    void update(Buyer buyer);

    void delete(int buyerId);
}