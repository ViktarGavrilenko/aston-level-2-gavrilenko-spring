package org.example.repository;

import org.example.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query("select i from items i join fetch i.orders o where o.id = :idOrder")
    List<Item> findAllByOrder(@Param("idOrder") int idOrder);
}
