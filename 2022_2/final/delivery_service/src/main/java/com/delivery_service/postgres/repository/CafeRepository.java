package com.delivery_service.postgres.repository;

import com.delivery_service.postgres.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Integer> {
    Cafe findById(long id);
    Optional<Cafe> findByName(String name);
    boolean existsById(long id);
}
