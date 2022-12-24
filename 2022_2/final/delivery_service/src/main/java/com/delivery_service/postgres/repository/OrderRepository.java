package com.delivery_service.postgres.repository;

import com.delivery_service.postgres.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findOrderById(long id);

    List<Order> findOrdersByIsActive(boolean isActive);

    boolean existsById(long id);
}