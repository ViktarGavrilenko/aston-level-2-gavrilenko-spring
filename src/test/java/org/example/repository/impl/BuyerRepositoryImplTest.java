package org.example.repository.impl;

import org.example.models.Buyer;
import org.example.models.Order;
import org.example.repository.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.example.controlers.BuyerControllerTest.getTemplateBuyer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
class BuyerRepositoryImplTest extends BaseTest {
    private final BuyerRepositoryImpl buyerRepository;
    private final OrderRepositoryImpl orderRepository;

    @Autowired
    public BuyerRepositoryImplTest(BuyerRepositoryImpl buyerRepository, OrderRepositoryImpl orderRepository,
                                   JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.buyerRepository = buyerRepository;
        this.orderRepository = orderRepository;
    }

    @Test
    void get() {
        Buyer saveBuyer = saveBuyer(getTemplateBuyer(1));
        Buyer getBuyer = buyerRepository.get(saveBuyer.getId());
        assertEquals(getBuyer, getTemplateBuyer(1));
    }

    @Test
    void getAll() {
        int size = 5;
        List<Buyer> buyers = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            Buyer buyer = saveBuyer(getTemplateBuyer(i));
            buyers.add(buyer);
        }
        List<Buyer> getAllBuyers = buyerRepository.getAll();
        assertEquals(buyers, getAllBuyers);
    }

    @Test
    void save() {
        Buyer saveBuyer = saveBuyer(getTemplateBuyer(1));
        Buyer getBuyer = buyerRepository.get(saveBuyer.getId());
        assertEquals(getBuyer, getTemplateBuyer(1));
    }

    @Test
    void update() {
        Buyer saveBuyer = saveBuyer(getTemplateBuyer(1));
        saveBuyer.setName("NewName");
        buyerRepository.update(saveBuyer);
        Buyer getUpdateBuyer = buyerRepository.get(saveBuyer.getId());
        assertEquals(getUpdateBuyer, saveBuyer);
    }

    @Test
    void delete() {
        Buyer saveBuyer = saveBuyer(getTemplateBuyer(1));
        assertEquals(buyerRepository.get(saveBuyer.getId()), saveBuyer);
        buyerRepository.delete(saveBuyer.getId());
        assertNull(buyerRepository.get(saveBuyer.getId()));
    }

    private Buyer saveBuyer(Buyer buyer) {
        List<Order> orders = buyer.getOrders();
        for (Order order : orders) {
            orderRepository.save(order);
        }
        return buyerRepository.save(buyer);
    }
}