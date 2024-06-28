package org.example.repository.impl;


import org.example.models.Buyer;
import org.example.models.Order;
import org.example.repository.BuyerRepository;
import org.example.repository.mapper.BuyerResultSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BuyerRepositoryImpl implements BuyerRepository {
    public static final String SELECT_ALL_BUYERS = "SELECT id, name FROM buyers";
    public static final String SELECT_BUYERS = "SELECT id, name FROM buyers where id=?";
    public static final String BUYER_BY_NAME = "SELECT id, name FROM buyers where name=?";
    public static final String BUYER_ORDER_BY_ID_BUYER_AND_ID_ORDER =
            "SELECT id FROM buyer_order where id_buyer=? and id_order=?";
    public static final String INSERT_BUYER = "INSERT INTO buyers (name) VALUES (?)";
    public static final String INSERT_BUYER_ORDERS = "INSERT INTO buyer_order (id_buyer, id_order) VALUES (?, ?)";

    public static final String ORDERS_OF_BUYER = "SELECT id_order FROM buyer_order WHERE id_buyer=?;";
    public static final String SELECT_ID_ITEMS_OF_ORDER_BY_ID_ORDER =
            "SELECT id_item FROM order_items WHERE id_order=?;";
    public static final String DELETE_BUYER_BY_ID = "DELETE FROM buyers WHERE id = ?;";
    public static final String DELETE_ORDER_OF_BUYER_FROM_BUYER_ORDER = "DELETE FROM buyer_order WHERE id_buyer = ?;";
    public static final String DELETE_ORDER_OF_BUYER_FROM_BUYER_ORDER_BY_ID_ORDER =
            "DELETE FROM buyer_order WHERE id_order = ?;";
    public static final String UPDATE_BUYER_BY_ID = "UPDATE buyers SET name=? where id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final OrderRepositoryImpl orderRepository;

    @Autowired
    public BuyerRepositoryImpl(JdbcTemplate jdbcTemplate, OrderRepositoryImpl orderRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderRepository = orderRepository;
    }

    @Override
    public Buyer get(Integer id) {
        return jdbcTemplate.queryForObject(SELECT_BUYERS, new BuyerResultSetMapper(orderRepository), id);
    }

    @Override
    public List<Buyer> getAll() {
        return jdbcTemplate.query(SELECT_ALL_BUYERS, new BuyerResultSetMapper(orderRepository));
    }

    @Override
    @Transactional("transactionManager")
    public Buyer save(Buyer buyer) {
        if (jdbcTemplate.update(INSERT_BUYER, buyer.getName()) == 1) {
            Buyer saveBuyer = jdbcTemplate.queryForObject(BUYER_BY_NAME,
                    new BeanPropertyRowMapper<>(Buyer.class), buyer.getName());

            List<Order> orders = buyer.getOrders();
            for (Order order : orders) {
                if (orderRepository.get(order.getId()) == null) {
                    Order saveOrder = orderRepository.save(order);
                    jdbcTemplate.update(INSERT_BUYER_ORDERS, saveBuyer.getId(), saveOrder.getId());
                } else {
                    jdbcTemplate.update(INSERT_BUYER_ORDERS, saveBuyer.getId(), order.getId());
                }
            }
            return saveBuyer;
        } else {
            throw new IllegalArgumentException(ItemRepositoryImpl.SQL_QUERY_FAILED);
        }
    }

    @Override
    @Transactional("transactionManager")
    public void update(Buyer buyer) {
        jdbcTemplate.update(UPDATE_BUYER_BY_ID, buyer.getName(), buyer.getId());
        List<Order> newOrder = buyer.getOrders();
        List<Order> oldOrder = Optional.ofNullable(get(buyer.getId()).getOrders()).orElse(new ArrayList<>());
        List<Order> listOrderForAdd = new ArrayList<>(newOrder);
        listOrderForAdd.removeAll(oldOrder);
        List<Order> listOrderForDelete = new ArrayList<>(oldOrder);
        listOrderForDelete.removeAll(newOrder);
        for (Order order : listOrderForDelete) {
            jdbcTemplate.update(DELETE_ORDER_OF_BUYER_FROM_BUYER_ORDER_BY_ID_ORDER, order.getId());
            orderRepository.delete(order.getId());
        }
        updateNewOrders(listOrderForAdd, buyer);
    }

    private void updateNewOrders(List<Order> listOrderForAdd, Buyer buyer) {
        for (Order order : listOrderForAdd) {
            if (orderRepository.get(order.getId()) == null) {
                Order addOrder = orderRepository.save(order);
                jdbcTemplate.update(INSERT_BUYER_ORDERS, buyer.getId(), addOrder.getId());
            } else {
                Integer idBuyerOrder = null;
                try {
                    idBuyerOrder = jdbcTemplate.queryForObject(BUYER_ORDER_BY_ID_BUYER_AND_ID_ORDER,
                            new SingleColumnRowMapper<>(Integer.class), buyer.getId(), order.getId());
                } catch (EmptyResultDataAccessException ex) {

                }
                if (idBuyerOrder == null) {
                    jdbcTemplate.update(INSERT_BUYER_ORDERS, buyer.getId(), order.getId());
                }
                orderRepository.update(order);
            }
        }
    }

    @Override
    @Transactional("transactionManager")
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_BUYER_BY_ID, id);
        List<Order> orderList = orderRepository.getListOfBuyerOrdersById(id);
        for (Order order : orderList) {
            orderRepository.delete(order.getId());
        }
        jdbcTemplate.update(DELETE_ORDER_OF_BUYER_FROM_BUYER_ORDER, id);
    }
}