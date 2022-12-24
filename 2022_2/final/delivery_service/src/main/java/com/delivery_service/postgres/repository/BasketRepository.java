package com.delivery_service.postgres.repository;

import com.delivery_service.postgres.entity.Basket;
import com.delivery_service.postgres.entity.Order;
import com.delivery_service.postgres.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
    List<Basket> findBasketsByOrder(Order order);

    List<Basket> findBasketsByUserAndOrder_IsActive(User user, Boolean order_isActive);
    boolean existsById(long id);
    void deleteBasketById(long id);
}
