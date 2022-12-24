package com.delivery_service.postgres.repository;

import com.delivery_service.postgres.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
