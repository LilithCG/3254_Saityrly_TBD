package com.delivery_service.service.category;

import com.delivery_service.postgres.entity.Category;
import com.delivery_service.dto.request.CategoryRequest;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    void saveCategory(CategoryRequest category);

    List<Category> fetchCategoryList();

    Category fetchCategory(long id) throws ChangeSetPersister.NotFoundException;

    Optional<Category> fetchCategory(String name) throws ChangeSetPersister.NotFoundException;

    boolean exist(long id);
}
