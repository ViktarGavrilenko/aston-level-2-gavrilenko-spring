package org.example.repository.impl;


import org.example.models.Buyer;
import org.example.models.Order;
import org.example.repository.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BuyerRepositoryImpl implements BuyerRepository {
    public static final String ID_BUYERS = "SELECT id FROM buyers where 1";
    public static final String SELECT_BUYERS = "SELECT id, name FROM buyers where id=%s";
    public static final String BUYER_BY_NAME = "SELECT id, name FROM buyers where name='%s'";
    public static final String BUYER_ORDER_BY_ID_BUYER_AND_ID_ORDER =
            "SELECT id FROM buyer_order where id_buyer=%s and id_order=%s";
    public static final String INSERT_BUYER = "INSERT INTO buyers (name) VALUES ('%s')";
    public static final String INSERT_BUYER_ORDERS = "INSERT INTO buyer_order (id_buyer, id_order) VALUES (%s, %s)";

    public static final String ORDERS_OF_BUYER = "SELECT id_order FROM buyer_order WHERE id_buyer=%s;";
    public static final String SELECT_ID_ITEMS_OF_ORDER_BY_ID_ORDER =
            "SELECT id_item FROM order_items WHERE id_order=?;";
    public static final String DELETE_BUYER_BY_ID = "DELETE FROM buyers WHERE id = %s;";
    public static final String DELETE_ORDER_OF_BUYER_FROM_BUYER_ORDER = "DELETE FROM buyer_order WHERE id_buyer = %s;";
    public static final String DELETE_ORDER_OF_BUYER_FROM_BUYER_ORDER_BY_ID_ORDER =
            "DELETE FROM buyer_order WHERE id_order = %s;";
    public static final String UPDATE_BUYER_BY_ID = "UPDATE buyers SET name='%s' where id = '%s'";


    private final JdbcTemplate jdbcTemplate;
    private final OrderRepositoryImpl orderRepository;

    @Autowired
    public BuyerRepositoryImpl(JdbcTemplate jdbcTemplate, OrderRepositoryImpl orderRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderRepository = orderRepository;
    }

    @Override
    public Buyer get(Integer id) {
        return buyerResultSetMapper.map(connectionProvider.sendSelectQuery(String.format(SELECT_BUYERS, id)));
    }

    @Override
    public List<Buyer> getAll() {
        List<Integer> idBuyers = connectionProvider.getListFirstColumnInt(ID_BUYERS);
        List<Buyer> buyers = new ArrayList<>();
        for (int idBuyer : idBuyers) {
            buyers.add(get(idBuyer));
        }
        return buyers;
    }

    @Override
    public Buyer save(Buyer buyer) {
        connectionProvider.setAutoCommitFalse();
        if (connectionProvider.sendSqlQuery(String.format(INSERT_BUYER, buyer.getName()))) {
            int idBuyer = buyerResultSetMapper.map(
                    connectionProvider.sendSelectQuery(String.format(BUYER_BY_NAME, buyer.getName()))).getId();
            List<Order> orders = buyer.getOrders();
            for (Order order : orders) {
                if (orderRepository.get(order.getId()) == null) {
                    Order saveOrder = orderRepository.save(order);
                    connectionProvider.sendSqlQuery(String.format(INSERT_BUYER_ORDERS, idBuyer, saveOrder.getId()));
                } else {
                    connectionProvider.sendSqlQuery(String.format(INSERT_BUYER_ORDERS, idBuyer, order.getId()));
                }
                connectionProvider.setAutoCommitFalse();
            }
            connectionProvider.completeTransaction();
            connectionProvider.setAutoCommitTrue();
            return get(idBuyer);
        } else {
            connectionProvider.setAutoCommitTrue();
            throw new IllegalArgumentException(SQL_QUERY_FAILED);
        }
    }

    @Override
    public void update(Buyer buyer) {
        connectionProvider.setAutoCommitFalse();
        connectionProvider.sendSqlQuery(String.format(UPDATE_BUYER_BY_ID, buyer.getName(), buyer.getId()));
        List<Order> newOrder = buyer.getOrders();
        List<Order> oldOrder = get(buyer.getId()).getOrders();
        List<Order> listOrderForAdd = new ArrayList<>(newOrder);
        listOrderForAdd.removeAll(oldOrder);
        List<Order> listOrderForDelete = new ArrayList<>(oldOrder);
        listOrderForDelete.removeAll(newOrder);
        for (Order order : listOrderForDelete) {
            connectionProvider.sendSqlQuery(String.format(DELETE_ORDER_OF_BUYER_FROM_BUYER_ORDER_BY_ID_ORDER, order.getId()));
            orderRepository.delete(order.getId());
        }
        updateNewOrders(listOrderForAdd, buyer);
        connectionProvider.completeTransaction();
        connectionProvider.setAutoCommitTrue();
    }

    private void updateNewOrders(List<Order> listOrderForAdd, Buyer buyer) {
        for (Order order : listOrderForAdd) {
            if (orderRepository.get(order.getId()) == null) {
                Order addOrder = orderRepository.save(order);
                connectionProvider.sendSqlQuery(String.format(INSERT_BUYER_ORDERS, buyer.getId(), addOrder.getId()));
            } else {
                ResultSet resultSet = connectionProvider.sendSelectQuery(String.format(BUYER_ORDER_BY_ID_BUYER_AND_ID_ORDER, buyer.getId(), order.getId()));
                try {
                    if (!resultSet.next()) {
                        connectionProvider.sendSqlQuery(String.format(INSERT_BUYER_ORDERS, buyer.getId(), order.getId()));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                orderRepository.update(order);
            }
        }
        connectionProvider.setAutoCommitFalse();
    }

    @Override
    public void delete(Integer id) {
        connectionProvider.setAutoCommitFalse();
        connectionProvider.sendSqlQuery(String.format(DELETE_BUYER_BY_ID, id));
        List<Order> orderList = orderRepository.getListOfBuyerOrdersById(id);
        for (Order order : orderList) {
            orderRepository.delete(order.getId());
            connectionProvider.setAutoCommitFalse();
        }
        connectionProvider.sendSqlQuery(String.format(DELETE_ORDER_OF_BUYER_FROM_BUYER_ORDER, id));
        connectionProvider.completeTransaction();
        connectionProvider.setAutoCommitTrue();
    }
}