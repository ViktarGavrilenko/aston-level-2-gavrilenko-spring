package org.example.repository.impl;

import org.example.models.Item;
import org.example.models.Order;
import org.example.repository.ItemRepository;
import org.example.repository.mapper.ItemResultSetMapper;
import org.example.repository.mapper.ItemResultSetMapperWithOutOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.repository.impl.BuyerRepositoryImpl.INVALID_ORDER_ID;
import static org.example.repository.impl.BuyerRepositoryImpl.SELECT_ID_ITEMS_OF_ORDER_BY_ID_ORDER;
import static org.example.repository.impl.OrderRepositoryImpl.INSERT_ORDER_ITEMS;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    public static final String SQL_QUERY_FAILED = "Sql query failed...";

    public static final String ITEM_BY_ID = "SELECT id, name, price FROM items where id=?";
    public static final String ITEM_BY_NAME_AND_PRICE = "SELECT id, name, price FROM items where name=? and price=?";
    public static final String SELECT_ITEMS = "SELECT id, name, price FROM items";
    public static final String INSERT_ITEM = "INSERT INTO items(name, price) VALUES (?, ?)";
    public static final String DELETE_ITEM_BY_ID = "DELETE FROM items where id = ?";
    public static final String DELETE_ITEM_FROM_ORDER_ITEMS = "DELETE FROM order_items WHERE id_item = ?";
    public static final String DELETE_ORDER_ITEMS = "DELETE FROM order_items WHERE id_order = ? and id_item=?";
    public static final String UPDATE_ITEM_BY_ID = "UPDATE items SET name=?, price=? where id =?";

    private final JdbcTemplate jdbcTemplate;
    private final OrderRepositoryImpl orderRepository;

    @Autowired
    public ItemRepositoryImpl(JdbcTemplate jdbcTemplate, @Lazy OrderRepositoryImpl orderRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderRepository = orderRepository;
    }

    @Override
    public Item get(Integer id) {
        try {
            return jdbcTemplate.queryForObject(ITEM_BY_ID, new ItemResultSetMapper(orderRepository), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Item> getAll() {
        return jdbcTemplate.query(SELECT_ITEMS, new ItemResultSetMapper(orderRepository));
    }

    @Override
    public Item save(Item item) {
        if (jdbcTemplate.update(INSERT_ITEM, item.getName(), item.getPrice()) == 1) {
            Item saveItem = jdbcTemplate.queryForObject(ITEM_BY_NAME_AND_PRICE, new BeanPropertyRowMapper<>(Item.class),
                    item.getName(), item.getPrice());

            List<Order> orders = item.getOrders();
            for (Order order : orders) {
                if (orderRepository.get(order.getId()) != null) {
                    jdbcTemplate.update(INSERT_ORDER_ITEMS, order.getId(), saveItem.getId());
                } else {
                    throw new IllegalArgumentException(INVALID_ORDER_ID);
                }
            }
            saveItem.setOrders(orders);
            return saveItem;
        } else {
            throw new IllegalArgumentException(SQL_QUERY_FAILED);
        }
    }

    @Override
    @Transactional("transactionManager")
    public void update(Item item) {
        jdbcTemplate.update(UPDATE_ITEM_BY_ID, item.getName(), item.getPrice(), item.getId());
        List<Order> newOrder = item.getOrders();
        List<Order> oldOrder = Optional.ofNullable(get(item.getId()).getOrders()).orElse(new ArrayList<>());
        List<Order> ordersForAdd = new ArrayList<>(newOrder);
        ordersForAdd.removeAll(oldOrder);
        List<Order> ordersForDelete = new ArrayList<>(oldOrder);
        ordersForDelete.removeAll(newOrder);
        for (Order order : ordersForDelete) {
            jdbcTemplate.update(DELETE_ORDER_ITEMS, order.getId(), item.getId());
        }
        for (Order order : ordersForAdd) {
            jdbcTemplate.update(INSERT_ORDER_ITEMS, order.getId(), item.getId());
        }
    }

    @Override
    @Transactional("transactionManager")
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_ITEM_FROM_ORDER_ITEMS, id);
        jdbcTemplate.update(DELETE_ITEM_BY_ID, id);
    }

    public List<Item> getListItemsInOrderById(int idOrder) {
        List<Integer> idItems = jdbcTemplate.query(SELECT_ID_ITEMS_OF_ORDER_BY_ID_ORDER,
                new SingleColumnRowMapper<>(Integer.class), idOrder);
        List<Item> items = new ArrayList<>();
        for (int idItem : idItems) {
            Item item = jdbcTemplate.queryForObject(ITEM_BY_ID, new ItemResultSetMapperWithOutOrder(), idItem);
            items.add(item);
        }
        return items;
    }
}