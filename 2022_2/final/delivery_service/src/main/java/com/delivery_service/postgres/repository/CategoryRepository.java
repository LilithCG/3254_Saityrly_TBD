package com.delivery_service.postgres.repository;

import com.delivery_service.postgres.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findById(long id);
    Optional<Category> findByName(String name);
    boolean existsById(long id);
}
