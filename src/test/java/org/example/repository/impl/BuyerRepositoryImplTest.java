package org.example.repository.impl;

import org.example.models.Buyer;
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

import static org.example.controlers.BuyerControllerTest.buyerList;
import static org.example.controlers.BuyerControllerTest.getTemplateBuyer;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
class BuyerRepositoryImplTest extends BaseTest {
    private final BuyerRepositoryImpl buyerRepository;

    @Autowired
    public BuyerRepositoryImplTest(BuyerRepositoryImpl buyerRepository, JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.buyerRepository = buyerRepository;
    }

    @Test
    void shouldGetCustomers() {
        Buyer buyer = getTemplateBuyer(1);
        List<Buyer> buyers = buyerRepository.getAll();
        assertEquals(0, buyers.size());
        buyerRepository.save(buyer);
        buyers = buyerRepository.getAll();
        assertEquals(1, buyers.size());
    }

    @Test
    void get() {
        Buyer saveBuyer = buyerRepository.save(getTemplateBuyer(1));
        Buyer getBuyer = buyerRepository.get(saveBuyer.getId());
        assertEquals(getBuyer, getTemplateBuyer(1));
    }

    @Test
    void getAll() {
        int size = 5;
        List<Buyer> buyers = buyerList(size);
        List<Buyer> saveBuyers = new ArrayList<>();
        for (Buyer buyer : buyers) {
            Buyer saveBuyer = buyerRepository.save(buyer);
            saveBuyers.add(saveBuyer);
        }
        List<Buyer> getAllBuyers = buyerRepository.getAll();
        assertTrue(getAllBuyers.equals(buyers));
    }

    @Test
    void save() {
        Buyer saveBuyer = buyerRepository.save(getTemplateBuyer(1));
        Buyer getBuyer = buyerRepository.get(saveBuyer.getId());
        assertEquals(getBuyer, getTemplateBuyer(1));
    }

    @Test
    void update() {
        Buyer saveBuyer = buyerRepository.save(getTemplateBuyer(1));
        saveBuyer.setName("NewName");
        buyerRepository.update(saveBuyer);
        Buyer getUpdateBuyer = buyerRepository.get(saveBuyer.getId());
        assertEquals(getUpdateBuyer, saveBuyer);
    }

    @Test
    void delete() {
        Buyer saveBuyer = buyerRepository.save(getTemplateBuyer(1));
        assertEquals(buyerRepository.get(saveBuyer.getId()), saveBuyer);
        buyerRepository.delete(saveBuyer.getId());
        assertNull(buyerRepository.get(saveBuyer.getId()));
    }
}