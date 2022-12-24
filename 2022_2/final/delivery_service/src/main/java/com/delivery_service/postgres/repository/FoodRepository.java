package com.delivery_service.postgres.repository;

import com.delivery_service.postgres.entity.Cafe;
import com.delivery_service.postgres.entity.Category;
import com.delivery_service.postgres.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Integer> {
    Food findById(long id);

    List<Food> findByCafeAndCategoryAndIsActive(Cafe cafe, Category category, boolean isActive);

    List<Food> findByCafe(Cafe cafe);

    List<Food> findByCafeAndIsActiveAndNameLikeIgnoreCase(Cafe cafe, Boolean isActive, String name);
    boolean existsById(long id);
}
