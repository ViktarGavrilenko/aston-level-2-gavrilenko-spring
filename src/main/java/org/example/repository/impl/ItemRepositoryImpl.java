package org.example.repository.impl;

import org.example.models.Item;
import org.example.models.Order;
import org.example.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.example.repository.impl.BuyerRepositoryImpl.SELECT_ID_ITEMS_OF_ORDER_BY_ID_ORDER;
import static org.example.repository.impl.OrderRepositoryImpl.INSERT_ORDER_ITEMS;

public class ItemRepositoryImpl implements ItemRepository {
    public static final String SQL_QUERY_FAILED = "Sql query failed...";

    public static final String ITEM_BY_ID = "SELECT id, name, price FROM items where id=?";
    public static final String ITEM_BY_NAME_AND_PRICE = "SELECT id, name, price FROM items where name=? and price=?";
    public static final String SELECT_ITEMS = "SELECT id, name, price FROM items";
    public static final String INSERT_ITEM = "INSERT INTO items(name, price) VALUES (?, ?)";
    public static final String DELETE_ITEM_BY_ID = "DELETE FROM items where id = %s";
    public static final String DELETE_ITEM_FROM_ORDER_ITEMS = "DELETE FROM order_items WHERE id_item = ?";
    public static final String DELETE_ORDER_ITEMS = "DELETE FROM order_items WHERE id_order = ? and id_item=?";
    public static final String UPDATE_ITEM_BY_ID = "UPDATE items SET name=?, price=? where id =?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ItemRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Item get(Integer id) {
        return jdbcTemplate.queryForObject(ITEM_BY_ID, new BeanPropertyRowMapper<>(Item.class), id);
    }


    @Override
    public List<Item> getAll() {
        return jdbcTemplate.query(SELECT_ITEMS, new BeanPropertyRowMapper<>(Item.class));
    }


    @Override
    public Item save(Item item) {
        if (jdbcTemplate.update(INSERT_ITEM, item.getName(), item.getPrice()) == 1) {
            Item saveItem = jdbcTemplate.queryForObject(ITEM_BY_NAME_AND_PRICE, new BeanPropertyRowMapper<>(Item.class),
                    item.getName(), item.getPrice());

            List<Order> orders = item.getOrders();
            for (Order order : orders) {
                jdbcTemplate.update(INSERT_ORDER_ITEMS, order.getId(), saveItem.getId());
            }
            return saveItem;
        } else {
            throw new IllegalArgumentException(SQL_QUERY_FAILED);
        }
    }


    @Override
    @Transactional
    public void update(Item item) {
        jdbcTemplate.update(UPDATE_ITEM_BY_ID, item.getName(), item.getPrice(), item.getId());
        List<Order> newOrder = item.getOrders();
        List<Order> oldOrder = get(item.getId()).getOrders();
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
    @Transactional
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_ITEM_FROM_ORDER_ITEMS, id);
        jdbcTemplate.update(DELETE_ITEM_BY_ID, id);
    }

    public List<Item> getListItemsInOrderById(int idOrder) {
        List<Integer> idItems = jdbcTemplate.query(SELECT_ID_ITEMS_OF_ORDER_BY_ID_ORDER, new Object[]{idOrder},
                new BeanPropertyRowMapper<>(Integer.class));
        List<Item> items = new ArrayList<>();
        for (int idItem : idItems) {
            Item item = jdbcTemplate.queryForObject(ITEM_BY_ID, new Object[]{idItem}, new BeanPropertyRowMapper<>(Item.class));
            items.add(item);
        }
        return items;
    }
}