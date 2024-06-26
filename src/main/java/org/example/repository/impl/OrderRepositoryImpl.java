package org.example.repository.impl;

import org.example.models.Item;
import org.example.models.Order;
import org.example.repository.OrderRepository;


import java.util.ArrayList;
import java.util.List;

import static org.example.repository.impl.BuyerRepositoryImpl.ORDERS_OF_BUYER;

public class OrderRepositoryImpl implements OrderRepository {
    public static final String ID_ORDERS = "SELECT id FROM orders where 1";
    public static final String ORDER_BY_NUMBER = "SELECT id, number FROM orders where number=%s";
    public static final String ORDER_BY_ID = "SELECT id, number FROM orders where id=%s";
    public static final String INSERT_ORDER = "INSERT INTO orders (number) VALUES (%s)";
    public static final String INSERT_ORDER_ITEMS = "INSERT INTO order_items(id_order, id_item) VALUES (?, ?)";
    public static final String UPDATE_ORDER_BY_ID = "UPDATE orders SET number=%s where id = '%s'";
    public static final String DELETE_ORDER_ITEMS = "DELETE FROM order_items WHERE id_order = %s and id_item=%s";
    public static final String DELETE_ORDER = "DELETE FROM orders WHERE id = %s ";
    public static final String DELETE_ORDER_FROM_ORDER_ITEMS = "DELETE FROM order_items WHERE id_order = %s";
    public static final String DELETE_BUYER_ORDER_BY_ID_ORDER = "DELETE FROM buyer_order WHERE id_order = %s";
    public static final String SELECT_ID_ORDERS_OF_ORDER_BY_ID_ITEM =
            "SELECT id_order FROM order_items WHERE id_item=%s;";

    static OrderResultSetMapperImpl orderResultSetMapper = new OrderResultSetMapperImpl();
    DBConnectionProvider connectionProvider;

    public OrderRepositoryImpl(DBConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public OrderRepositoryImpl() {
        this.connectionProvider = new DBConnectionProvider();
    }

    @Override
    public Order get(Integer id) {
        return orderResultSetMapper.map(connectionProvider.sendSelectQuery(String.format(ORDER_BY_ID, id)));
    }

    @Override
    public List<Order> getAll() {
        List<Integer> idOrders = connectionProvider.getListFirstColumnInt(ID_ORDERS);
        List<Order> orders = new ArrayList<>();
        for (int idOrder : idOrders) {
            orders.add(get(idOrder));
        }
        return orders;
    }

    @Override
    public Order save(Order order) {
        connectionProvider.setAutoCommitFalse();
        if (connectionProvider.sendSqlQuery(String.format(INSERT_ORDER, order.getNumber()))) {
            int idOrder = orderResultSetMapper.map(
                    connectionProvider.sendSelectQuery(String.format(ORDER_BY_NUMBER, order.getNumber()))).getId();
            List<Item> items = order.getItems();
            for (Item item : items) {
                connectionProvider.sendSqlQuery(String.format(INSERT_ORDER_ITEMS, idOrder, item.getId()));
            }
            connectionProvider.completeTransaction();
            connectionProvider.setAutoCommitTrue();
            return get(idOrder);
        } else {
            connectionProvider.setAutoCommitTrue();
            throw new IllegalArgumentException(SQL_QUERY_FAILED);
        }
    }

    @Override
    public void update(Order order) {
        connectionProvider.setAutoCommitFalse();
        connectionProvider.sendSqlQuery(String.format(UPDATE_ORDER_BY_ID, order.getNumber(), order.getId()));
        List<Item> newItems = order.getItems();
        List<Item> oldItem = get(order.getId()).getItems();
        List<Item> listForAdd = new ArrayList<>(newItems);
        listForAdd.removeAll(oldItem);
        List<Item> listForDelete = new ArrayList<>(oldItem);
        listForDelete.removeAll(newItems);
        for (Item item : listForDelete) {
            connectionProvider.sendSqlQuery(String.format(DELETE_ORDER_ITEMS, order.getId(), item.getId()));
        }
        for (Item item : listForAdd) {
            connectionProvider.sendSqlQuery(String.format(INSERT_ORDER_ITEMS, order.getId(), item.getId()));
        }
        connectionProvider.completeTransaction();
        connectionProvider.setAutoCommitTrue();
    }

    @Override
    public void delete(Integer id) {
        connectionProvider.setAutoCommitFalse();
        connectionProvider.sendSqlQuery(String.format(DELETE_ORDER_FROM_ORDER_ITEMS, id));
        connectionProvider.sendSqlQuery(String.format(DELETE_ORDER, id));
        connectionProvider.completeTransaction();
        connectionProvider.setAutoCommitTrue();
    }

    public List<Order> getListOfBuyerOrdersById(int idBuyer) {
        List<Integer> idOrders = connectionProvider.getListFirstColumnInt(String.format(ORDERS_OF_BUYER, idBuyer));
        List<Order> orders = new ArrayList<>();
        for (int idOrder : idOrders) {
            Order order = get(idOrder);
            if (order == null) {
                connectionProvider.sendSqlQuery(String.format(DELETE_BUYER_ORDER_BY_ID_ORDER, idOrder));
            } else {
                orders.add(order);
            }
        }
        return orders;
    }

    public List<Order> getListOrderByIdItem(int idItem) {
        List<Integer> idOrders = connectionProvider.getListFirstColumnInt(String.format(SELECT_ID_ORDERS_OF_ORDER_BY_ID_ITEM, idItem));
        List<Order> orders = new ArrayList<>();
        for (int idOrder : idOrders) {
            Order order = orderResultSetMapper.mapWithOutItems(connectionProvider.sendSelectQuery(String.format(ORDER_BY_ID, idOrder)));
            orders.add(order);
        }
        return orders;
    }
}