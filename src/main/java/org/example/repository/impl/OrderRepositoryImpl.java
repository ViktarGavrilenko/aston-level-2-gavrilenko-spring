package org.example.repository.impl;

import org.example.models.Item;
import org.example.models.Order;
import org.example.repository.OrderRepository;
import org.example.repository.mapper.OrderResultMapperWithOutItems;
import org.example.repository.mapper.OrderResultSetMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    public static final String SELECT_ALL_ORDERS = "SELECT id, number FROM orders";
    public static final String ORDER_BY_NUMBER = "SELECT id, number FROM orders where number=?";
    public static final String ORDER_BY_ID = "SELECT id, number FROM orders where id=?";
    public static final String INSERT_ORDER = "INSERT INTO orders (number) VALUES (?)";
    public static final String INSERT_ORDER_ITEMS = "INSERT INTO order_items(id_order, id_item) VALUES (?, ?)";
    public static final String UPDATE_ORDER_BY_ID = "UPDATE orders SET number=? where id = ?";
    public static final String DELETE_ORDER_ITEMS = "DELETE FROM order_items WHERE id_order = ? and id_item=?";
    public static final String DELETE_ORDER = "DELETE FROM orders WHERE id = ? ";
    public static final String DELETE_ORDER_FROM_ORDER_ITEMS = "DELETE FROM order_items WHERE id_order = ?";
    public static final String DELETE_BUYER_ORDER_BY_ID_ORDER = "DELETE FROM buyer_order WHERE id_order = ?";
    public static final String SELECT_ID_ORDERS_OF_ORDER_BY_ID_ITEM =
            "SELECT id_order FROM order_items WHERE id_item=?;";

    private final JdbcTemplate jdbcTemplate;
    private final ItemRepositoryImpl itemRepository;

    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate, ItemRepositoryImpl itemRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.itemRepository = itemRepository;
    }

    @Override
    public Order get(Integer id) {
        return jdbcTemplate.queryForObject(ORDER_BY_ID, new OrderResultSetMapper(itemRepository), id);
    }

    @Override
    public List<Order> getAll() {
        return jdbcTemplate.query(SELECT_ALL_ORDERS, new OrderResultSetMapper(itemRepository));
    }

    @Override
    @Transactional("transactionManager")
    public Order save(Order order) {
        if (jdbcTemplate.update(INSERT_ORDER, order.getNumber()) == 1) {
            Order saveOrder = jdbcTemplate.queryForObject(ORDER_BY_NUMBER, new BeanPropertyRowMapper<>(Order.class), order.getNumber());
            List<Item> items = order.getItems();
            for (Item item : items) {
                jdbcTemplate.update(INSERT_ORDER_ITEMS, saveOrder.getId(), item.getId());
            }
            return saveOrder;
        } else {
            throw new IllegalArgumentException(ItemRepositoryImpl.SQL_QUERY_FAILED);
        }
    }

    @Override
    @Transactional("transactionManager")
    public void update(Order order) {
        jdbcTemplate.update(UPDATE_ORDER_BY_ID, order.getNumber(), order.getId());
        List<Item> newItems = order.getItems();
        List<Item> oldItem = Optional.ofNullable(get(order.getId()).getItems()).orElse(new ArrayList<>());
        List<Item> listForAdd = new ArrayList<>(newItems);
        listForAdd.removeAll(oldItem);
        List<Item> listForDelete = new ArrayList<>(oldItem);
        listForDelete.removeAll(newItems);
        for (Item item : listForDelete) {
            jdbcTemplate.update(DELETE_ORDER_ITEMS, order.getId(), item.getId());
        }
        for (Item item : listForAdd) {
            jdbcTemplate.update(INSERT_ORDER_ITEMS, order.getId(), item.getId());
        }
    }

    @Override
    @Transactional("transactionManager")
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_ORDER_FROM_ORDER_ITEMS, id);
        jdbcTemplate.update(DELETE_ORDER, id);
        jdbcTemplate.update(DELETE_BUYER_ORDER_BY_ID_ORDER, id);
    }

    public List<Order> getListOfBuyerOrdersById(int idBuyer) {
        List<Integer> idOrders = jdbcTemplate.query(BuyerRepositoryImpl.ORDERS_OF_BUYER, new SingleColumnRowMapper<>(Integer.class), idBuyer);
        List<Order> orders = new ArrayList<>();
        for (int idOrder : idOrders) {
            Order order = get(idOrder);
            if (order == null) {
                jdbcTemplate.update(DELETE_BUYER_ORDER_BY_ID_ORDER, idOrder);
            } else {
                orders.add(order);
            }
        }
        return orders;
    }

    public List<Order> getListOrderByIdItem(int idItem) {
        List<Integer> idOrders = jdbcTemplate.query(SELECT_ID_ORDERS_OF_ORDER_BY_ID_ITEM,
                new SingleColumnRowMapper<>(Integer.class), idItem);
        List<Order> orders = new ArrayList<>();
        for (int idOrder : idOrders) {
            Order order = jdbcTemplate.queryForObject(ORDER_BY_ID, new OrderResultMapperWithOutItems(), idOrder);
            orders.add(order);
        }
        return orders;
    }
}