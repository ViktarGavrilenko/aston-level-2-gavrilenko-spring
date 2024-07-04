package org.example.services.impl;

import org.example.models.Buyer;
import org.example.repository.impl.BuyerRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.example.controlers.BuyerControllerTest.buyerList;
import static org.example.controlers.BuyerControllerTest.getTemplateBuyer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuyerServiceImplTest {
    @Mock
    private BuyerRepositoryImpl buyerRepository;
    @InjectMocks
    private BuyerServiceImpl buyerService;

    @Test
    void getAll() {
        when(buyerRepository.getAll()).thenReturn(buyerList(5));
        List<Buyer> getAllBuyer = buyerService.getAll();
        Assertions.assertEquals(getAllBuyer, buyerList(5));
    }

    @Test
    void get() {
        when(buyerRepository.get(Mockito.anyInt())).thenReturn(getTemplateBuyer(1));
        Buyer getBuyer = buyerService.get(1);
        Assertions.assertEquals(getBuyer, getTemplateBuyer(1));
    }

    @Test
    void save() {
        when(buyerRepository.save(Mockito.any(Buyer.class))).thenReturn(getTemplateBuyer(1));
        Buyer saveBuyer = buyerService.save(getTemplateBuyer(1));
        Assertions.assertEquals(saveBuyer, getTemplateBuyer(1));
    }

    @Test
    void update() {
        buyerService.update(getTemplateBuyer(1));
        Mockito.verify(buyerRepository).update(getTemplateBuyer(1));
    }

    @Test
    void delete() {
        buyerService.delete(1);
        Mockito.verify(buyerRepository).delete(1);
    }
}