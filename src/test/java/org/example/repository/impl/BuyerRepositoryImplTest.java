package org.example.repository.impl;

import org.example.config.SpringConfig;
import org.example.models.Buyer;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.example.controlers.BuyerControllerTest.getTemplateBuyer;
import static org.junit.jupiter.api.Assertions.assertEquals;

@PropertySource("classpath:database.properties")
@Testcontainers
/*@ExtendWith(SpringExtension.class)*/
@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
class BuyerRepositoryImplTest {

    private final BuyerRepositoryImpl buyerRepository;

    @Value("${database.properties.bdName}")
    private static String dbName="task2_aston";
    @Value("${database.properties.bdUser}")
    private static String dbUser="root";
    @Value("${database.properties.bdPass}")
    private static String bdPass="";

    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>(
            "mysql:8"
    ).withDatabaseName(dbName)
            .withUsername(dbUser)
            .withPassword(bdPass)
            .withInitScript("schema.sql");

    @Autowired
    public BuyerRepositoryImplTest(BuyerRepositoryImpl buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    @BeforeClass
    public static void beforeAll() {
        mySQLContainer.start();
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
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}