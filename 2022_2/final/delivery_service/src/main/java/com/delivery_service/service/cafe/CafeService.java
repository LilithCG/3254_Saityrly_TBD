package com.delivery_service.service.cafe;

import com.delivery_service.postgres.entity.Cafe;
import com.delivery_service.dto.request.CafeRequest;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

public interface CafeService {
    void saveCafe(CafeRequest cafe);
    List<Cafe> fetchCafeList();
    Cafe fetchCafe(long id) throws ChangeSetPersister.NotFoundException;
    Optional<Cafe> fetchCafe(String name) throws ChangeSetPersister.NotFoundException;
    boolean exist(long id);
}
