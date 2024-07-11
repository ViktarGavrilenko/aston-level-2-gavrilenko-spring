package org.example.repository;

import org.example.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select o from orders o join fetch o.buyers b where b.id = :idBuyer")
    List<Order> findAllByIdBuyer(@Param("idBuyer") int idOrder);

    @Query("select o from orders o join fetch o.items i where i.id = :idItem")
    List<Order> findAllByIdItem(@Param("idItem") int idOrder);
}
